package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Cinema;
import model.Room;

public class CinemaDAO implements dao.ICinemaDAO {
	// Get all cinema
	@Override
	public List<Cinema> getAllCinema() {
		List<Cinema> list = new ArrayList<>();
		try {
			String query = "SELECT cinema_id, cinema_name, cinema_address FROM cinemas;";
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
				cinema = new Cinema(id, name, address);
				list.add(cinema);
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Get cinema by id
	@Override
	public Cinema getCinemaById(int id) {
		Cinema cinema = null;
		try {
			String query = "SELECT cinema_id, cinema_name, cinema_address FROM cinemas WHERE cinema_id = ?;";
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
				cinema = new Cinema(id, name, address);
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cinema;
	}
	
	// Add cinema
	@Override
	public boolean addCinema(Cinema cinema) {
		try {
			String query = "INSERT INTO cinemas (cinema_name, cinema_address) VALUES (?, ?);";
			// Create connect
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setString(1, cinema.getName());
			st.setString(2, cinema.getAddress());
			st.executeUpdate();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// Delete cinema by id
	@Override
	public boolean deleteCinemaById(int id) {
		try {
			String query = "DELETE FROM cinemas WHERE cinema_id = ?;";
			// Create connect
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, id);
			st.executeUpdate();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int updateCinema(int id, Cinema newCinema) {
		int update = 0;
		// Query string to get data
		String queryString = "UPDATE cinemas SET cinema_name = ?, cinema_address = ?  WHERE cinema_id = ?";
		try {
			// Create connection
			Connection connect = dao.JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, newCinema.getName());
			ps.setString(2, newCinema.getAddress());
			ps.setInt(3, id);
			update = ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}

	// Tìm kiếm tên rạp phim bao gồm chi tiết rạp và phim đang được chiếu tại rạp
	public List<Cinema> searchCinemaByName(String keyword){
		List<Cinema> list = new ArrayList<>();
		try{
			String sql = "SELECT cinema_id, cinema_name, cinema_address FROM cinemas WHERE cinema_name LIKE ? ";
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
