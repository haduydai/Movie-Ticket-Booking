package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.CinemaStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Cinema;
import model.Movie;
import model.PaymentMethod;
import model.Role;
import model.Room;
import model.ShowTime;
import model.ShowTimeSeat;
import model.Ticket;
import model.TicketStatus;
import model.User;

public class TicketDAO implements ITicketDAO {

	private static final Logger logger = Logger.getLogger(TicketDAO.class.getName());
	// get tickets by user id
	@Override
	public List<Ticket> getTicketsByUserId(int userId) {
		List<Ticket> list = new ArrayList<>();
		String sql = "SELECT t.ticket_id, t.ticket_uid, t.ticket_price, t.payment_method, t.ticket_status, t.ticket_seats, t.created_at, t.updated_at,"
				+ "u.user_id, u.username, u.email, u.phonenumber, u.role," 
				+ "m.movie_id,  m.movie_name,"
				+ "c.cinema_id, c.cinema_name, c.cinema_address," 
				+ "r.room_id, r.room_name,"
				+ "s.showtime_id, s.start_time, s.showtime_price " 
				+ "FROM tickets t " 
				+ "JOIN users u ON t.user_id = u.user_id " 
				+ "JOIN showtimes s ON t.showtime_id = s.showtime_id "
				+ "JOIN movies m ON s.movie_id = m.movie_id " 
				+ "JOIN cinemas c ON s.cinema_id = c.cinema_id "
				+ "JOIN rooms r ON s.room_id = r.room_id " 
				+ "WHERE u.user_id = ? "
				+ "ORDER BY t.created_at DESC;";
		try (Connection conn = JDBCConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					list.add(mapResultSetToTicket(rs));
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in getTicketsByUserId", e);
		}

		return list;
	}

	// Get ticket by ticket id
	@Override
	public Ticket getTicketById(int ticketId) {
		String sql = "SELECT t.ticket_id, t.ticket_uid, t.ticket_price, t.payment_method, t.ticket_status, t.ticket_seats, t.created_at, t.updated_at,"
				+ "u.user_id, u.username, u.email, u.phonenumber, u.role," 
				+ "m.movie_id,  m.movie_name,"
				+ "c.cinema_id, c.cinema_name, c.cinema_address," 
				+ "r.room_id, r.room_name,"
				+ "s.showtime_id, s.start_time, s.showtime_price " 
				+ "FROM tickets t " 
				+ "JOIN users u ON t.user_id = u.user_id " 
				+ "JOIN showtimes s ON t.showtime_id = s.showtime_id "
				+ "JOIN movies m ON s.movie_id = m.movie_id " 
				+ "JOIN cinemas c ON s.cinema_id = c.cinema_id "
				+ "JOIN rooms r ON s.room_id = r.room_id " 
				+ "WHERE t.ticket_id = ? "
				+ "ORDER BY t.created_at DESC;";
		try (Connection conn = JDBCConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, ticketId);
			try (ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					return mapResultSetToTicket(rs);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in getTicketById", e);
		}
		return null;
	}

	// get all tickets
	@Override
	public List<Ticket> getAllTickets() {
		List<Ticket> list = new ArrayList<>();
		String sql = "SELECT t.ticket_id, t.ticket_uid, t.ticket_price, t.payment_method, t.ticket_status, t.ticket_seats, t.created_at, t.updated_at,"
				+ "u.user_id, u.username, u.email, u.phonenumber, u.role," 
				+ "m.movie_id,  m.movie_name,"
				+ "c.cinema_id, c.cinema_name, c.cinema_address," 
				+ "r.room_id, r.room_name,"
				+ "s.showtime_id, s.start_time, s.showtime_price " 
				+ "FROM tickets t " 
				+ "JOIN users u ON t.user_id = u.user_id " 
				+ "JOIN showtimes s ON t.showtime_id = s.showtime_id "
				+ "JOIN movies m ON s.movie_id = m.movie_id " 
				+ "JOIN cinemas c ON s.cinema_id = c.cinema_id "
				+ "JOIN rooms r ON s.room_id = r.room_id " 
				+ "ORDER BY t.created_at DESC;";
		try (Connection conn = JDBCConnection.getConnection();
			 Statement st = conn.createStatement()){
			try (ResultSet rs = st.executeQuery(sql)){
				while (rs.next()) {
					list.add(mapResultSetToTicket(rs));
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in getAllTickets", e);
		}

		return list;
	}

	// Add new ticket
	@Override
	public boolean saveBooking(User user, int showtimeId, String[] seats, double totalPrice, String paymentMethod) {
		Connection conn = null;
		PreparedStatement psTicket = null;
		PreparedStatement psSeat = null;
		ResultSet rs = null;

		try {
			conn = JDBCConnection.getConnection();
			conn.setAutoCommit(false); // Bắt đầu Transaction

			// 1. Tạo mã vé (Ticket UID)
			String ticketUid = UUID.randomUUID().toString();

			// 2. Insert vào bảng TICKETS (Giữ nguyên)
			String sqlTicket = "INSERT INTO tickets (ticket_uid, ticket_price, payment_method, ticket_status, ticket_seats, user_id, showtime_id) VALUES (?, ?, ?, 'PAID', ?, ?, ?)";
			psTicket = conn.prepareStatement(sqlTicket, Statement.RETURN_GENERATED_KEYS);
			psTicket.setString(1, ticketUid);
			psTicket.setBigDecimal(2, java.math.BigDecimal.valueOf(totalPrice));
			psTicket.setString(3, paymentMethod);
			psTicket.setString(4, convertListToString(seats));
			psTicket.setInt(5, user.getId());
			psTicket.setInt(6, showtimeId);
			psTicket.executeUpdate();

			int newTicketId = 0;
			rs = psTicket.getGeneratedKeys();
			if (rs.next()) {
				newTicketId = rs.getInt(1);
			} else {
				throw new SQLException("Không lấy được ID vé vừa tạo.");
			}
			// 3. Update bảng SHOWTIMESEATS (THAY ĐỔI Ở ĐÂY: Dùng UPDATE thay vì INSERT)
			// Tìm đúng ghế của suất chiếu đó và cập nhật user_id
			String sqlSeat = "UPDATE showtimeseats SET user_id = ?, ticket_id = ? WHERE showtime_id = ? AND seat_name = ? AND user_id IS NULL";
			psSeat = conn.prepareStatement(sqlSeat);

			for (String seat : seats) {
				// Cập nhật ghế (Tham số theo thứ tự dấu ? trong sqlSeat)
				psSeat.setInt(1, user.getId());
				psSeat.setInt(2, newTicketId);
				psSeat.setInt(3, showtimeId);
				psSeat.setString(4, seat.trim());
				psSeat.addBatch();
			}

			int[] updateCounts = psSeat.executeBatch();

			// Kiểm tra xem có ghế nào update thất bại không
			for (int count : updateCounts) {
				if (count == 0) {
					// Nếu count == 0 tức là không tìm thấy ghế để update (Sai tên hoặc sai ID)
					System.out.println("LỖI: Không tìm thấy ghế trong DB để update. Kiểm tra lại tên ghế!");
					throw new SQLException("Lỗi: Ghế không tồn tại hoặc đã bị người khác đặt.");
				}
			}
			conn.commit(); // Xác nhận lưu
			return true;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in saveBooking", e);
			try {
				if (conn != null)
					conn.rollback(); // Gặp lỗi thì hoàn tác
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "Rollback failed in saveBooking", ex);
			}
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Failed to close connection in saveBooking", e);
			}
		}
	}

	@Override
	public boolean updateTicketStatus(Ticket ticket, TicketStatus newStatus) {
		String sql = "UPDATE tickets SET ticket_status = ? WHERE ticket_id = ?;";
		try {
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newStatus.name());
			ps.setInt(2, ticket.getId());
			ps.executeUpdate();
			ps.close();
			conn.close();
			IShowTimeSeatDAO seatDAO = new ShowTimeSeatDAO();
			// If user cancelled ticket => all show time seat in that ticket will be not
			// booked
			if (newStatus.equals(TicketStatus.CANCELLED)) {
				for (ShowTimeSeat sts : seatDAO.getShowTimeSeatsByShowTimeAndUserAndTicket(ticket.getShowTime().getId(), ticket.getUser().getId(), ticket.getId())) {
					seatDAO.updateShowTimeSeat(sts.getId(), null, 0);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in updateTicketStatus", e);
			return false;
		}
		return true;
	}

	private Ticket mapResultSetToTicket(ResultSet rs) {
		Ticket ticket = new Ticket();
		try {
			User user = new User(rs.getInt("user_id"), rs.getString("username"), null, rs.getString("email"),
					rs.getString("phonenumber"), Role.valueOf(rs.getString("role")));

			// Cinema constructor expects (int, String, String, CinemaStatus)
			Cinema cinema = new Cinema(rs.getInt("cinema_id"), rs.getString("cinema_name"),
					rs.getString("cinema_address"), CinemaStatus.OPEN);

			Room room = new Room(rs.getInt("room_id"), rs.getString("room_name"));

			Movie movie = new Movie(rs.getInt("movie_id"), rs.getString("movie_name"));

			ShowTime showTime = new ShowTime(rs.getInt("showtime_id"), cinema, room, movie,
					rs.getBigDecimal("showtime_price"), rs.getTimestamp("start_time").toLocalDateTime(), null);

			int ticketId = rs.getInt("ticket_id");

			ticket = new Ticket(ticketId, rs.getString("ticket_uid"), user, showTime, rs.getString("ticket_seats"),
					rs.getBigDecimal("ticket_price"), PaymentMethod.valueOf(rs.getString("payment_method")),
					TicketStatus.valueOf(rs.getString("ticket_status")),
					rs.getTimestamp("created_at").toLocalDateTime(), rs.getTimestamp("updated_at").toLocalDateTime());
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error mapping ResultSet to Ticket", e);
		}
		return ticket;
	}
	
	// Convert list of seats to seat have ',' between 2 seat
	// EX: A1, A2,... , B5
	private String convertListToString(String[] seats) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < seats.length; i++) {
			sb.append(seats[i]);
			if(i < seats.length - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

}
