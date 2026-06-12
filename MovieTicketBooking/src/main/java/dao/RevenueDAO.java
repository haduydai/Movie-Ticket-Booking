package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal;

public class RevenueDAO {
	private static final Logger logger = Logger.getLogger(RevenueDAO.class.getName());

	/**
	 * Get daily revenue for a date range
	 * @param startDate Start date (inclusive)
	 * @param endDate End date (inclusive)
	 * @return List of maps containing date, movie, cinema, and revenue
	 */
	public List<Map<String, Object>> getDailyRevenue(LocalDate startDate, LocalDate endDate) {
		List<Map<String, Object>> list = new ArrayList<>();
		String query = "SELECT DATE(t.created_at) as revenue_date, "
				+ "m.movie_id, m.movie_name, "
				+ "c.cinema_id, c.cinema_name, "
				+ "COUNT(t.ticket_id) as ticket_count, "
				+ "SUM(t.ticket_price) as total_revenue "
				+ "FROM tickets t "
				+ "JOIN showtimes s ON t.showtime_id = s.showtime_id "
				+ "JOIN movies m ON s.movie_id = m.movie_id "
				+ "JOIN cinemas c ON s.cinema_id = c.cinema_id "
				+ "WHERE DATE(t.created_at) BETWEEN ? AND ? "
				+ "AND t.ticket_status != 'CANCELLED' "
				+ "GROUP BY DATE(t.created_at), m.movie_id, c.cinema_id "
				+ "ORDER BY revenue_date DESC, total_revenue DESC;";

		try (Connection conn = JDBCConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setDate(1, java.sql.Date.valueOf(startDate));
			ps.setDate(2, java.sql.Date.valueOf(endDate));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Map<String, Object> map = new HashMap<>();
					map.put("date", rs.getDate("revenue_date").toLocalDate());
					map.put("movieId", rs.getInt("movie_id"));
					map.put("movieName", rs.getString("movie_name"));
					map.put("cinemaId", rs.getInt("cinema_id"));
					map.put("cinemaName", rs.getString("cinema_name"));
					map.put("ticketCount", rs.getInt("ticket_count"));
					map.put("revenue", rs.getBigDecimal("total_revenue"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting daily revenue", e);
		}
		return list;
	}

	/**
	 * Get total revenue by movie for a date range
	 */
	public List<Map<String, Object>> getRevenueByMovie(LocalDate startDate, LocalDate endDate) {
		List<Map<String, Object>> list = new ArrayList<>();
		String query = "SELECT m.movie_id, m.movie_name, m.movie_duration, "
				+ "COUNT(t.ticket_id) as ticket_count, "
				+ "SUM(t.ticket_price) as total_revenue "
				+ "FROM tickets t "
				+ "JOIN showtimes s ON t.showtime_id = s.showtime_id "
				+ "JOIN movies m ON s.movie_id = m.movie_id "
				+ "WHERE DATE(t.created_at) BETWEEN ? AND ? "
				+ "AND t.ticket_status != 'CANCELLED' "
				+ "GROUP BY m.movie_id, m.movie_name, m.movie_duration "
				+ "ORDER BY total_revenue DESC;";

		try (Connection conn = JDBCConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setDate(1, java.sql.Date.valueOf(startDate));
			ps.setDate(2, java.sql.Date.valueOf(endDate));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Map<String, Object> map = new HashMap<>();
					map.put("movieId", rs.getInt("movie_id"));
					map.put("movieName", rs.getString("movie_name"));
					map.put("duration", rs.getInt("movie_duration"));
					map.put("ticketCount", rs.getInt("ticket_count"));
					map.put("revenue", rs.getBigDecimal("total_revenue"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting revenue by movie", e);
		}
		return list;
	}

	/**
	 * Get total revenue by cinema for a date range
	 */
	public List<Map<String, Object>> getRevenueBycinema(LocalDate startDate, LocalDate endDate) {
		List<Map<String, Object>> list = new ArrayList<>();
		String query = "SELECT c.cinema_id, c.cinema_name, c.cinema_address, "
				+ "COUNT(t.ticket_id) as ticket_count, "
				+ "SUM(t.ticket_price) as total_revenue "
				+ "FROM tickets t "
				+ "JOIN showtimes s ON t.showtime_id = s.showtime_id "
				+ "JOIN cinemas c ON s.cinema_id = c.cinema_id "
				+ "WHERE DATE(t.created_at) BETWEEN ? AND ? "
				+ "AND t.ticket_status != 'CANCELLED' "
				+ "GROUP BY c.cinema_id, c.cinema_name, c.cinema_address "
				+ "ORDER BY total_revenue DESC;";

		try (Connection conn = JDBCConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setDate(1, java.sql.Date.valueOf(startDate));
			ps.setDate(2, java.sql.Date.valueOf(endDate));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Map<String, Object> map = new HashMap<>();
					map.put("cinemaId", rs.getInt("cinema_id"));
					map.put("cinemaName", rs.getString("cinema_name"));
					map.put("address", rs.getString("cinema_address"));
					map.put("ticketCount", rs.getInt("ticket_count"));
					map.put("revenue", rs.getBigDecimal("total_revenue"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting revenue by cinema", e);
		}
		return list;
	}

	/**
	 * Get total summary revenue for a date range
	 */
	public Map<String, Object> getTotalRevenue(LocalDate startDate, LocalDate endDate) {
		Map<String, Object> map = new HashMap<>();
		String query = "SELECT COUNT(t.ticket_id) as total_tickets, "
				+ "SUM(t.ticket_price) as total_revenue, "
				+ "AVG(t.ticket_price) as avg_price, "
				+ "MIN(t.ticket_price) as min_price, "
				+ "MAX(t.ticket_price) as max_price "
				+ "FROM tickets t "
				+ "WHERE DATE(t.created_at) BETWEEN ? AND ? "
				+ "AND t.ticket_status != 'CANCELLED';";

		try (Connection conn = JDBCConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setDate(1, java.sql.Date.valueOf(startDate));
			ps.setDate(2, java.sql.Date.valueOf(endDate));
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					map.put("totalTickets", rs.getInt("total_tickets"));
					map.put("totalRevenue", rs.getBigDecimal("total_revenue"));
					map.put("avgPrice", rs.getBigDecimal("avg_price"));
					map.put("minPrice", rs.getBigDecimal("min_price"));
					map.put("maxPrice", rs.getBigDecimal("max_price"));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting total revenue", e);
		}
		return map;
	}

	/**
	 * Get revenue by payment method
	 */
	public List<Map<String, Object>> getRevenueByPaymentMethod(LocalDate startDate, LocalDate endDate) {
		List<Map<String, Object>> list = new ArrayList<>();
		String query = "SELECT t.payment_method, "
				+ "COUNT(t.ticket_id) as ticket_count, "
				+ "SUM(t.ticket_price) as total_revenue "
				+ "FROM tickets t "
				+ "WHERE DATE(t.created_at) BETWEEN ? AND ? "
				+ "AND t.ticket_status != 'CANCELLED' "
				+ "GROUP BY t.payment_method "
				+ "ORDER BY total_revenue DESC;";

		try (Connection conn = JDBCConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setDate(1, java.sql.Date.valueOf(startDate));
			ps.setDate(2, java.sql.Date.valueOf(endDate));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Map<String, Object> map = new HashMap<>();
					map.put("paymentMethod", rs.getString("payment_method"));
					map.put("ticketCount", rs.getInt("ticket_count"));
					map.put("revenue", rs.getBigDecimal("total_revenue"));
					list.add(map);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting revenue by payment method", e);
		}
		return list;
	}
}

