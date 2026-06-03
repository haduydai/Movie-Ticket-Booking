package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private Role role;
	private boolean locked; // NEW: account lock status
	private List<Ticket> tickets;

	public User() {}

	// Constructor for create user (register)
	public User(String username, String password, String email, String phoneNumber, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.locked = false;
		this.tickets = new ArrayList<>();
	}

	// Constructor for get user from db (without locked)
	public User(int id, String username, String password, String email, String phoneNumber, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.locked = false;
		this.tickets = new ArrayList<>();
	}

	// Constructor for get user from db (with locked)
	public User(int id, String username, String password, String email, String phoneNumber, Role role, boolean locked) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.locked = locked;
		this.tickets = new ArrayList<>();
	}

	// Getter and Setter
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }

	public boolean isLocked() { return locked; }
	public void setLocked(boolean locked) { this.locked = locked; }

	public List<Ticket> getTickets() { return tickets; }
	public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
}