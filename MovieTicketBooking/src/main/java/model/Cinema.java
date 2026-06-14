package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cinemas")
@SQLDelete(sql = "UPDATE cinemas SET deleted_at = NOW() WHERE cinema_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class Cinema extends AbsBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cinema_id")
	private int id;

	@Column(name = "cinema_name", nullable = false)
	private String name;

	@Column(name = "cinema_address", nullable = false)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(name = "cinema_status")
	private CinemaStatus status = CinemaStatus.OPEN;

	@OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<>();

	public Cinema() {}

	public Cinema(int id, String name, String address, CinemaStatus status) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.status = status;
		this.rooms = new ArrayList<>();
	}

	public Cinema(String name, String address) {
		this.name = name;
		this.address = address;
		this.rooms = new ArrayList<>();
	}

	public String getAllInfo() {
		return name + " - " + address;
	}
}