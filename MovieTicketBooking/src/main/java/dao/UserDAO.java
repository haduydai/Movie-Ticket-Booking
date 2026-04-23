package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Role;
import model.ShowTime;
import model.ShowTimeSeat;
import model.Ticket;
import model.User;
import utils.PasswordUtils;

public class UserDAO implements IUserDAO {
	// Get all user
	@Override
	public List<User> getAllUser() {
		List<User> list = new ArrayList<>();
		String query = "SELECT user_id, username, password, email, phonenumber, role FROM users";
		try (Connection connect = JDBCConnection.getConnection();
				PreparedStatement st = connect.prepareStatement(query)) {
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					list.add(mapResultSetToUser(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// Get user by user id
	@Override
	public User getUserById(int id) {
		User user = null;
		String query = "SELECT user_id, username, password, email, phonenumber, role FROM users WHERE user_id = ?;";
		try (Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					user = mapResultSetToUser(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	// Get user by username
	@Override
	public User checkUser(String username) {
		User user = null;
		String query = "SELECT user_id, username, password, email, phonenumber, role FROM users WHERE username = ?;";
		try (Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);){
			st.setString(1, username);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					user = mapResultSetToUser(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// Get user by email
	@Override
	public User getUserByEmail(String email) {
	    User user = null;
	    String query = "SELECT user_id, username, password, email, phonenumber, role FROM users WHERE email = ?";
	    try (Connection connect = JDBCConnection.getConnection();
	        PreparedStatement st = connect.prepareStatement(query);) {
	        st.setString(1, email);
	        try (ResultSet rs = st.executeQuery();) {
	        	if (rs.next()) {
		             user = mapResultSetToUser(rs);
		        }
	        } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}
	@Override
	public User getUserByPhone(String phone) {
		User user = null;
		String query = "SELECT user_id, username, password, email, phonenumber, role FROM users WHERE phonenumber = ?";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query);) {
			st.setString(1, phone);
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					user = mapResultSetToUser(rs);
				}
			} catch (SQLException e) { e.printStackTrace(); }
		} catch (SQLException e) { e.printStackTrace(); }
		return user;
	}
	// Add new user (register user)
	@Override
	public boolean addUser(User user) {
		String query = "INSERT INTO users (username, password, email, phonenumber, role) VALUES (?, ?, ?, ?, ?);";
		try (Connection connect = JDBCConnection.getConnection();
				PreparedStatement st = connect.prepareStatement(query);) {
			st.setString(1, user.getUsername());
			// Hash password of new user
			String hashPassowrd = PasswordUtils.hashPassword(user.getPassword());
			st.setString(2, hashPassowrd);
			st.setString(3, user.getEmail());
			st.setString(4, user.getPhoneNumber());
			st.setString(5, user.getRole().toString());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// Update user info by user id
	@Override
	public void updateUser(User user) {
		String query = " UPDATE users SET username = ?, email = ? , phonenumber = ? , password = ? WHERE user_id = ?";
		try (Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(query);){
			st.setString(1, user.getUsername());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPhoneNumber());
			st.setString(4, user.getPassword());
			st.setInt(5, user.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Delete user by id
	@Override
	public boolean deleteUser(int id) {
		try {
			// Query string to get data
			String queryString = "DELETE FROM users WHERE user_id = ?";
			// Create connection
			Connection connect = JDBCConnection.getConnection();
			PreparedStatement st = connect.prepareStatement(queryString);
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

	// Map result set to user
	private User mapResultSetToUser(ResultSet rs) {
		User user = null;
		try {
			int id = rs.getInt("user_id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String email = rs.getString("email");
			String phoneNumber = rs.getString("phonenumber");
			Role role = Role.valueOf(rs.getString("role"));
			user = new User(id, username, password, email, phoneNumber, role);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}


}
