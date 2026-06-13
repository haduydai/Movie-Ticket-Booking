package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Cinema;
import model.CinemaStatus;

public class CinemaDAO implements dao.ICinemaDAO {

	private static final Logger logger = Logger.getLogger(CinemaDAO.class.getName());

	// Get all cinema
	@Override
	public List<Cinema> getAllCinema() {
		List<Cinema> list = new ArrayList<>();
		try {
			String query = "SELECT cinema_id, cinema_name, cinema_address, cinema_status FROM cinemas WHERE deleted_at IS NULL;";
			// Create connect
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			int id;
			String name;
			String address;
			Cinema cinema;
			while (rs.next()) {
				id = rs.getInt("cinema_id");
				name = rs.getString("cinema_name");
				address = rs.getString("cinema_address");
				String statusStr = null;
				try {
					statusStr = rs.getString("cinema_status");
				} catch (SQLException ex) {
					// column might not exist in older schema
				}
				if (statusStr != null) {
					cinema = new Cinema(id, name, address, CinemaStatus.valueOf(statusStr));
				} else {
					cinema = new Cinema(id, name, address, CinemaStatus.OPEN);
				}
				list.add(cinema);
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error in getAllCinema", e);
		}
		return list;
	}

	// Get cinema by id
	@Override
	public Cinema getCinemaById(int id) {
		Cinema cinema = null;
		try {
			String query = "SELECT cinema_id, cinema_name, cinema_address, cinema_status FROM cinemas WHERE cinema_id = ? AND deleted_at IS NULL;";
			// Create connect
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			String name;
			String address;
			while (rs.next()) {
				id = rs.getInt("cinema_id");
				name = rs.getString("cinema_name");
				address = rs.getString("cinema_address");
				String statusStr = null;
				try {
					statusStr = rs.getString("cinema_status");
				} catch (SQLException ex) {
					// ignore
				}
				if (statusStr != null) {
					cinema = new Cinema(id, name, address, CinemaStatus.valueOf(statusStr));
				} else {
					cinema = new Cinema(id, name, address, CinemaStatus.OPEN);
				}
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error in getCinemaById", e);
		}
		return cinema;
	}
	
	// Add cinema
	@Override
	public boolean addCinema(Cinema cinema) {
		try {
			String query = "INSERT INTO cinemas (cinema_name, cinema_address, cinema_status) VALUES (?, ?, ?);";
			// Create connect
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setString(1, cinema.getName());
			st.setString(2, cinema.getAddress());
			st.setString(3, cinema.getStatus() != null ? cinema.getStatus().name() : model.CinemaStatus.OPEN.name());
			st.executeUpdate();
			st.close();
			connect.close();
		} catch (SQLException e) {
			// If column cinema_status doesn't exist, fallback to previous insert
			try {
				String query = "INSERT INTO cinemas (cinema_name, cinema_address) VALUES (?, ?);";
				Connection connect = JDBCConnection.getConnection();
				PreparedStatement st = connect.prepareStatement(query);
				st.setString(1, cinema.getName());
				st.setString(2, cinema.getAddress());
				st.executeUpdate();
				st.close();
				connect.close();
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "Error in addCinema fallback", ex);
				return false;
			}
		}
		return true;
	}

	// Delete cinema by id
	@Override
	public boolean deleteCinemaById(int id) {
		try {
			String query = "UPDATE cinemas SET deleted_at = NOW() WHERE cinema_id = ?;";
			// Create connect
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, id);
			st.executeUpdate();
			st.close();
			connect.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error in deleteCinemaById", e);
			return false;
		}
		return true;
	}

	@Override
	public int updateCinema(int id, Cinema newCinema) {
		int update = 0;
		// Query string to get data
		String queryString = "UPDATE cinemas SET cinema_name = ?, cinema_address = ?, cinema_status = ? WHERE cinema_id = ?";
		try {
			// Create connection
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, newCinema.getName());
			ps.setString(2, newCinema.getAddress());
			ps.setString(3, newCinema.getStatus() != null ? newCinema.getStatus().name() : model.CinemaStatus.OPEN.name());
			ps.setInt(4, id);
			update = ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error in updateCinema", e);
		}
		return update;
	}

	// Tìm kiếm tên rạp phim bao gồm chi tiết rạp và phim đang được chiếu tại rạp
	public List<Cinema> searchCinemaByName(String keyword){
		List<Cinema> list = new ArrayList<>();
		try{
			String sql = "SELECT cinema_id, cinema_name, cinema_address FROM cinemas WHERE cinema_name LIKE ? AND deleted_at IS NULL";
			Connection conn = dao.JDBCConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Cinema cinema = new Cinema();
				cinema.setId(rs.getInt("cinema_id"));
				cinema.setName(rs.getString("cinema_name"));
				cinema.setAddress(rs.getString("cinema_address"));
				list.add(cinema);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
            e.printStackTrace();
        }
		return list;
    }
}
