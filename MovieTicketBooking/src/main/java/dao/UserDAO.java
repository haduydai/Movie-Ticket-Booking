package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Role;
import model.User;
import utils.PasswordUtils;

public class UserDAO implements IUserDAO {

	@Override
	public List<User> getAllUser() {
		List<User> list = new ArrayList<>();
		String query = "SELECT user_id, username, password, email, phonenumber, role, is_locked FROM users";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query);
		     ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				list.add(mapResultSetToUser(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User getUserById(int id) {
		User user = null;
		String query = "SELECT user_id, username, password, email, phonenumber, role, is_locked FROM users WHERE user_id = ?";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query)) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) user = mapResultSetToUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User checkUser(String username) {
		User user = null;
		String query = "SELECT user_id, username, password, email, phonenumber, role, is_locked FROM users WHERE username = ?";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query)) {
			st.setString(1, username);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) user = mapResultSetToUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		String query = "SELECT user_id, username, password, email, phonenumber, role, is_locked FROM users WHERE email = ?";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query)) {
			st.setString(1, email);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) user = mapResultSetToUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserByPhone(String phone) {
		User user = null;
		String query = "SELECT user_id, username, password, email, phonenumber, role, is_locked FROM users WHERE phonenumber = ?";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query)) {
			st.setString(1, phone);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) user = mapResultSetToUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean addUser(User user) {
		String query = "INSERT INTO users (username, password, email, phonenumber, role, is_locked) VALUES (?, ?, ?, ?, ?, 0)";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query)) {
			st.setString(1, user.getUsername());
			st.setString(2, PasswordUtils.hashPassword(user.getPassword()));
			st.setString(3, user.getEmail());
			st.setString(4, user.getPhoneNumber());
			st.setString(5, user.getRole().toString());
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void updateUser(User user) {
		String query = "UPDATE users SET username = ?, email = ?, phonenumber = ?, password = ? WHERE user_id = ?";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query)) {
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

	// NEW: Lock or unlock account (soft lock, no hard delete)
	public boolean setUserLocked(int userId, boolean locked) {
		String query = "UPDATE users SET is_locked = ? WHERE user_id = ?";
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement(query)) {
			st.setBoolean(1, locked);
			st.setInt(2, userId);
			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Keep deleteUser for admin hard-delete if absolutely needed
	@Override
	public boolean deleteUser(int id) {
		try (Connection connect = JDBCConnection.getConnection();
		     PreparedStatement st = connect.prepareStatement("DELETE FROM users WHERE user_id = ?")) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private User mapResultSetToUser(ResultSet rs) {
		try {
			int id = rs.getInt("user_id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String email = rs.getString("email");
			String phoneNumber = rs.getString("phonenumber");
			Role role = Role.valueOf(rs.getString("role"));
			boolean locked = false;
			try { locked = rs.getBoolean("is_locked"); } catch (SQLException ignored) {}
			return new User(id, username, password, email, phoneNumber, role, locked);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}