package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Room;
import model.Cinema;

public class RoomDAO implements IRoomDAO{
	// Get all rooms
	@Override
	public List<Room> getAllRoom() {
		List<Room> list = new ArrayList<>();
		try {
			String query = "SELECT room_id, room_name, number_of_columns, number_of_rows, cinema_id FROM rooms WHERE deleted_at IS NULL";
			// Create connect
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				list.add(mapResultSetToRoom(rs));
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Get room by room id
	@Override
	public Room getRoomById(int roomId) {
		Room room = null;
		try {
			String query = "SELECT room_id, room_name, number_of_columns, number_of_rows, cinema_id FROM rooms WHERE room_id = ? AND deleted_at IS NULL";
			// Create connect
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, roomId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				room = mapResultSetToRoom(rs);
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return room;
	}
	
	// Get list of rooms by cinema id
	@Override
	public List<Room> getRoomByCinemaId(int id){
		List<Room> list = new ArrayList<>();
		try {
			String query = "SELECT room_id, room_name, number_of_columns, number_of_rows, cinema_id FROM rooms WHERE cinema_id = ? AND deleted_at IS NULL;";
			// Create connect
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				list.add(mapResultSetToRoom(rs));
			}
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Add room with cinema id, then add seats of room
	@Override
	public boolean addRoom(Room room, int cinemaId) {
		try {
			String query = "INSERT INTO rooms (room_name, number_of_columns, number_of_rows, cinema_id) VALUES (?, ?, ?, ?);";
			// Create connect
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);
			st.setString(1, room.getName());
			st.setInt(2, room.getNumberOfColumns());
			st.setInt(3, room.getNumberOfRows());
			st.setInt(4, cinemaId);
			
			st.executeUpdate();
			// Add seats of this room to db
			
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	// Delete room by id
	@Override
	public int deleteRoomById(int id) {
		int update = 0;
		try {
			// Query string to get data
			String queryString = "UPDATE rooms SET deleted_at = NOW() WHERE room_id = ?";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(queryString);
			st.setInt(1, id);
			update = st.executeUpdate();
			st.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}
	
	// Update room by id
	@Override
	public int updateRoom(int id, Room room) {
		int update = 0;
		// Query string to get data
		String queryString = "UPDATE rooms SET room_name = ?  WHERE cinema_id = ?";
		try {
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement ps = connect.prepareStatement(queryString);
			ps.setString(1, room.getName());
			ps.setInt(2, id);
			update = ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}
	
	private Room mapResultSetToRoom(ResultSet rs) {
		try {
			int id = rs.getInt("room_id");
			String name = rs.getString("room_name");
			int numberOfColumns = rs.getInt("number_of_columns");
			int numberOfRows = rs.getInt("number_of_rows");
			Cinema cinema = new CinemaDAO().getCinemaById(rs.getInt("cinema_id"));

			return new Room(id, name, numberOfColumns, numberOfRows, cinema);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}


}
