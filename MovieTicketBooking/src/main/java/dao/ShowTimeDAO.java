package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Cinema;
import model.Movie;
import model.Room;
import model.ShowTime;
import model.ShowTimeSeat;

public class ShowTimeDAO implements IShowTimeDAO {
	private static final Logger logger = Logger.getLogger(ShowTimeDAO.class.getName());
	private ICinemaDAO cinemaDAO;
	private IRoomDAO roomDAO;
	private IMovieDAO movieDAO;

	public ShowTimeDAO() {
		cinemaDAO = new CinemaDAO();
		roomDAO = new RoomDAO();
		movieDAO = new MovieDAO();
	}

	// Get all show time
	@Override
	public List<ShowTime> getAllShowTime() {
		List<ShowTime> list = new ArrayList<>();
		try {
			String query = "SELECT showtime_id, showtime_price, start_time, created_at, movie_id, cinema_id, room_id FROM showtimes;";
			Connection connect = JDBCConnection.getConnection();
			Statement st = connect.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				list.add(mapResultSetToShowTime(rs));
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Get show time by id
	@Override
	public ShowTime getShowTimeById(int id) {
		ShowTime showTime = null;
		try {
			String query = "SELECT showtime_id, showtime_price, start_time, created_at, movie_id, cinema_id, room_id FROM showtimes WHERE showtime_id = ?;";
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				showTime = mapResultSetToShowTime(rs);
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return showTime;
	}
	
	// Get show time in next n days by movie id
	// Ex: day = 7, now = 1/1/2026 => 1/1/2026 to 7/1/2026
	@Override
	public List<ShowTime> getShowTimesByMovieIdAndNextNDays(int movieId, int day) {
		List<ShowTime> list = new ArrayList<>();
		// Query chuẩn không có ngoặc đơn
		String query = "SELECT showtime_id, showtime_price, start_time, created_at, movie_id, cinema_id, room_id "
				+ "FROM showtimes " + "WHERE movie_id = ? " + "AND start_time >= NOW() "
				+ "AND start_time <= DATE_ADD(NOW(), INTERVAL ? DAY) " + "ORDER BY start_time ASC";
		try {
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, movieId);
			st.setInt(2, day);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				list.add(mapResultSetToShowTime(rs));
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Add show time and seats of it
	@Override
	public boolean addShowTime(ShowTime showTime) {
		String conflictQuery = "SELECT COUNT(*) AS cnt FROM showtimes s JOIN movies m ON s.movie_id = m.movie_id "
				+ "WHERE s.room_id = ? AND (? < DATE_ADD(s.start_time, INTERVAL m.movie_duration MINUTE)) "
				+ "AND (s.start_time < DATE_ADD(?, INTERVAL ? MINUTE))";
		String insertQuery = "INSERT INTO showtimes (showtime_price, start_time, movie_id, cinema_id, room_id) VALUES "
				+ "(?, ?, ?, ?, ?);";
		try {
			Connection connect = JDBCConnection.getConnection();
			// Check conflict
			PreparedStatement chk = connect.prepareStatement(conflictQuery);
			chk.setInt(1, showTime.getRoomId());
			chk.setTimestamp(2, Timestamp.valueOf(showTime.getStartTime()));
			chk.setTimestamp(3, Timestamp.valueOf(showTime.getStartTime()));
			chk.setInt(4, showTime.getMovie().getDuration());
			ResultSet rchk = chk.executeQuery();
			if (rchk.next()) {
				int cnt = rchk.getInt("cnt");
				rchk.close();
				chk.close();
				connect.close();
				if (cnt > 0) {
					// conflict exists
					return false;
				}
			}
			// No conflict; insert
			connect.setAutoCommit(false);
			PreparedStatement st = connect.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
			st.setBigDecimal(1, showTime.getPricePerTicket());
			st.setTimestamp(2, Timestamp.valueOf(showTime.getStartTime()));
			st.setInt(3, showTime.getMovieId());
			st.setInt(4, showTime.getCinemaId());
			st.setInt(5, showTime.getRoomId());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (!rs.next()) {
				connect.rollback();
				st.close();
				connect.close();
				return false;
			}
			int newShowTimeId = rs.getInt(1);
			rs.close();
			st.close();
			connect.commit();
			connect.close();
			// Create seats and add to db
			ShowTime newShowTime = new ShowTime(newShowTimeId, showTime.getCinema(), showTime.getRoom(), showTime.getMovie(), showTime.getPricePerTicket(), showTime.getStartTime(), LocalDateTime.now());
			List<ShowTimeSeat> seats = newShowTime.createListShowTimeSeats();
			IShowTimeSeatDAO stsDAO = new ShowTimeSeatDAO();
			stsDAO.addShowTimeSeats(seats);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Delete show time by showtime id
	@Override
	public boolean deleteShowTimeById(int id) {
		try {
			String query = "DELETE FROM showtimes WHERE showtime_id = ?;";
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, id);
			int rowsAffected = st.executeUpdate();
			st.close();
			connect.close();
			if (rowsAffected > 0) {
				logger.log(Level.INFO, "Showtime deleted successfully with ID: " + id);
				return true;
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error deleting showtime: " + id, e);
		}
		return false;
	}

	private ShowTime mapResultSetToShowTime(ResultSet rs) {
		ShowTime showTime = null;
		try {
			int id = rs.getInt("showtime_id");
			BigDecimal price = rs.getBigDecimal("showtime_price");
			LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
			LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
			Movie movie = movieDAO.getMovieById(rs.getInt("movie_id"));
			Cinema cinema = cinemaDAO.getCinemaById(rs.getInt("cinema_id"));
			Room room = roomDAO.getRoomById(rs.getInt("room_id"));
			showTime = new ShowTime(id, cinema, room, movie, price, startTime, createdAt);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error mapping ResultSet to ShowTime", e);
		}
		return showTime;
	}

}
