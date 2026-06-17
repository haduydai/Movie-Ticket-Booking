package dao;

import java.util.UUID;
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
import model.Booking;
import model.BookingStatus;
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
		String sql = "SELECT t.ticket_id, t.ticket_uid, t.ticket_price, t.payment_method, t.ticket_status, t.ticket_seats, t.created_at, t.updated_at, t.booking_id, b.booking_status,"
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
				+ "LEFT JOIN bookings b ON t.booking_id = b.booking_id "
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
		String sql = "SELECT t.ticket_id, t.ticket_uid, t.ticket_price, t.payment_method, t.ticket_status, t.ticket_seats, t.created_at, t.updated_at, t.booking_id, b.booking_status,"
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
				+ "LEFT JOIN bookings b ON t.booking_id = b.booking_id "
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
		String sql = "SELECT t.ticket_id, t.ticket_uid, t.ticket_price, t.payment_method, t.ticket_status, t.ticket_seats, t.created_at, t.updated_at, t.booking_id, b.booking_status,"
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
				+ "LEFT JOIN bookings b ON t.booking_id = b.booking_id "
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
			conn.setAutoCommit(false);


			int bookingId = 0;
			String sqlBooking = "INSERT INTO bookings (user_id, total_price, final_price, booking_status) VALUES (?, ?, ?, 'UNPAID')";
			try (PreparedStatement psBooking = conn.prepareStatement(sqlBooking, Statement.RETURN_GENERATED_KEYS)) {
				psBooking.setInt(1, user.getId());
				psBooking.setBigDecimal(2, java.math.BigDecimal.valueOf(totalPrice));
				psBooking.setBigDecimal(3, java.math.BigDecimal.valueOf(totalPrice));
				psBooking.executeUpdate();
				try (ResultSet rsB = psBooking.getGeneratedKeys()) {
					if (rsB.next()) {
						bookingId = rsB.getInt(1);
					}
				}
			}
			if (bookingId == 0) {
				throw new SQLException("Không khởi tạo được hóa đơn Booking.");
			}


			String ticketUid = UUID.randomUUID().toString();


			String sqlTicket = "INSERT INTO tickets (ticket_uid, ticket_price, payment_method, ticket_status, ticket_seats, user_id, showtime_id, booking_id) VALUES (?, ?, ?, 'UNPAID', ?, ?, ?, ?)";
			psTicket = conn.prepareStatement(sqlTicket, Statement.RETURN_GENERATED_KEYS);
			psTicket.setString(1, ticketUid);
			psTicket.setBigDecimal(2, java.math.BigDecimal.valueOf(totalPrice));
			psTicket.setString(3, paymentMethod);
			psTicket.setString(4, convertListToString(seats));
			psTicket.setInt(5, user.getId());
			psTicket.setInt(6, showtimeId);
			psTicket.setInt(7, bookingId);
			psTicket.executeUpdate();

			int newTicketId = 0;
			rs = psTicket.getGeneratedKeys();
			if (rs.next()) {
				newTicketId = rs.getInt(1);
			} else {
				throw new SQLException("Không lấy được ID vé vừa tạo.");
			}


			String sqlSeat = "UPDATE showtimeseats SET user_id = ?, ticket_id = ? WHERE showtime_id = ? AND seat_name = ? AND user_id IS NULL";
			psSeat = conn.prepareStatement(sqlSeat);

			for (String seat : seats) {

				psSeat.setInt(1, user.getId());
				psSeat.setInt(2, newTicketId);
				psSeat.setInt(3, showtimeId);
				psSeat.setString(4, seat.trim());
				psSeat.addBatch();
			}

			int[] updateCounts = psSeat.executeBatch();


			for (int count : updateCounts) {
				if (count == 0) {


					throw new SQLException("Lỗi: Ghế không tồn tại hoặc đã bị người khác đặt.");
				}
			}
			conn.commit();
			return true;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in saveBooking", e);
			try {
				if (conn != null)
					conn.rollback();
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
			

			String bStatus = null;
			if (newStatus == TicketStatus.PAID) bStatus = "PAID";
			else if (newStatus == TicketStatus.CANCELLED) bStatus = "CANCELLED";
			else if (newStatus == TicketStatus.UNPAID) bStatus = "UNPAID";
			
			if (bStatus != null) {
				String sqlBooking = "UPDATE bookings b JOIN tickets t ON b.booking_id = t.booking_id SET b.booking_status = ? WHERE t.ticket_id = ?;";
				try (PreparedStatement psB = conn.prepareStatement(sqlBooking)) {
					psB.setString(1, bStatus);
					psB.setInt(2, ticket.getId());
					psB.executeUpdate();
				}
			}
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

			int bookingId = rs.getInt("booking_id");
			if (!rs.wasNull()) {
				Booking booking = new Booking();
				booking.setId(bookingId);
				String bStatus = rs.getString("booking_status");
				if (bStatus != null) {
					booking.setStatus(model.BookingStatus.valueOf(bStatus));
				}
				ticket.setBooking(booking);
			}
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


	public int getLastetTicketIdByUser(int userId){
		String sql = "SELECT ticket_id FROM tickets WHERE user_id = ? ORDER BY ticket_id DESC LIMIT 1";
		try{
			Connection conn = JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt("ticket_id");
			}

		} catch(Exception e){
			logger.log(Level.SEVERE, "Error in getLastetTicketIdByUser", e);
		}
		return -1;
	}

	public int getTicketElapsedSeconds(int ticketId) {
		String sql = "SELECT TIMESTAMPDIFF(SECOND, created_at, NOW()) AS elapsed FROM tickets WHERE ticket_id = ?";
		try (Connection conn = JDBCConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, ticketId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("elapsed");
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in getTicketElapsedSeconds", e);
		}
		return 0;
	}

	public void cleanupExpiredTickets() {
		String selectSql = "SELECT ticket_id FROM tickets WHERE ticket_status = 'UNPAID' AND created_at < DATE_SUB(NOW(), INTERVAL 30 SECOND)";
		String updateTicketSql = "UPDATE tickets SET ticket_status = 'CANCELLED' WHERE ticket_id = ?";
		String releaseSeatSql = "UPDATE showtimeseats SET user_id = NULL, ticket_id = NULL WHERE ticket_id = ?";
		try (Connection conn = JDBCConnection.getConnection()) {
			conn.setAutoCommit(false);
			List<Integer> expiredTicketIds = new ArrayList<>();
			try (PreparedStatement psSelect = conn.prepareStatement(selectSql);
				 ResultSet rs = psSelect.executeQuery()) {
				while (rs.next()) {
					expiredTicketIds.add(rs.getInt("ticket_id"));
				}
			}
			if (!expiredTicketIds.isEmpty()) {
				try (PreparedStatement psUpdate = conn.prepareStatement(updateTicketSql)) {
					for (int id : expiredTicketIds) {
						psUpdate.setInt(1, id);
						psUpdate.addBatch();
					}
					psUpdate.executeBatch();
				}

				String updateBookingSql = "UPDATE bookings b JOIN tickets t ON b.booking_id = t.booking_id SET b.booking_status = 'CANCELLED' WHERE t.ticket_id = ?";
				try (PreparedStatement psUpdateB = conn.prepareStatement(updateBookingSql)) {
					for (int id : expiredTicketIds) {
						psUpdateB.setInt(1, id);
						psUpdateB.addBatch();
					}
					psUpdateB.executeBatch();
				}
				try (PreparedStatement psRelease = conn.prepareStatement(releaseSeatSql)) {
					for (int id : expiredTicketIds) {
						psRelease.setInt(1, id);
						psRelease.addBatch();
					}
					psRelease.executeBatch();
				}
			}
			conn.commit();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in cleanupExpiredTickets", e);
		}
	}

	public boolean savePaymentTransaction(int bookingId, double amount, String paymentMethod, String providerRef, String paymentUrl, String status) {
		String sql = "INSERT INTO payment_transactions (booking_id, amount, payment_method, provider_transaction_ref, payment_url, status, paid_at) VALUES (?, ?, ?, ?, ?, ?, NOW())";
		try (Connection conn = JDBCConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, bookingId);
			ps.setBigDecimal(2, java.math.BigDecimal.valueOf(amount));
			ps.setString(3, paymentMethod);
			ps.setString(4, providerRef);
			ps.setString(5, paymentUrl);
			ps.setString(6, status);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in savePaymentTransaction", e);
			return false;
		}
	}

}
