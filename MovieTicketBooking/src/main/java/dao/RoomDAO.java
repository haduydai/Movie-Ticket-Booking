package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Room;
import model.Cinema;

public class RoomDAO implements IRoomDAO{
	private static final Logger logger = Logger.getLogger(RoomDAO.class.getName());
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
		Connection connect = null;
		PreparedStatement st = null;
		try {
			connect = JDBCConnection.getConnection();
			connect.setAutoCommit(false);
			
			String query = "INSERT INTO rooms (room_name, number_of_columns, number_of_rows, cinema_id) VALUES (?, ?, ?, ?);";
			st = connect.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			st.setString(1, room.getName());
			st.setInt(2, room.getNumberOfColumns());
			st.setInt(3, room.getNumberOfRows());
			st.setInt(4, cinemaId);
			st.executeUpdate();
			
			int roomId = 0;
			try (ResultSet rs = st.getGeneratedKeys()) {
				if (rs.next()) {
					roomId = rs.getInt(1);
				}
			}
			
			if (roomId > 0) {

				String insertSeatQuery = "INSERT INTO seats (room_id, row_label, column_number, seat_type) VALUES (?, ?, ?, ?);";
				try (PreparedStatement psSeat = connect.prepareStatement(insertSeatQuery)) {
					int totalRows = room.getNumberOfRows();
					int totalCols = room.getNumberOfColumns();
					for (int r = 0; r < totalRows; r++) {
						char rowLabel = (char) ('A' + r);
						String seatType = "REGULAR";
						

						if (r == 0 || r == 1) {
							seatType = "VIP";
						} 

						else if (r == totalRows - 1 && totalRows >= 4) {
							seatType = "SWEETBOX";
						}
						
						for (int c = 1; c <= totalCols; c++) {
							psSeat.setInt(1, roomId);
							psSeat.setString(2, String.valueOf(rowLabel));
							psSeat.setInt(3, c);
							psSeat.setString(4, seatType);
							psSeat.addBatch();
						}
					}
					psSeat.executeBatch();
				}
			}
			connect.commit();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error in addRoom", e);
			if (connect != null) {
				try {
					connect.rollback();
				} catch (SQLException ex) {
					logger.log(Level.SEVERE, "Rollback failed in addRoom", ex);
				}
			}
			return false;
		} finally {
			try {
				if (st != null) st.close();
				if (connect != null) connect.close();
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Failed to close connections in addRoom", e);
			}
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
		String queryString = "UPDATE rooms SET room_name = ?  WHERE room_id = ?";
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
			logger.log(Level.SEVERE, "Error in updateRoom", e);
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
