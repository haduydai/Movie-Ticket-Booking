package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE user_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class User extends AbsBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ticket> tickets = new ArrayList<>();

	public User() {}

	// Constructor phục vụ việc tạo mới user
	public User(String username, String password,
	            String email, String phoneNumber, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.tickets = new ArrayList<>();
	}

	// Constructor phục vụ việc lấy user từ database
	public User(int id, String username, String password,
	            String email, String phoneNumber, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.tickets = new ArrayList<>();
	}
}