package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "rooms")
@SQLDelete(sql = "UPDATE rooms SET deleted_at = NOW() WHERE room_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class Room extends AbsBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int id;

	@Column(name = "room_name", nullable = false)
	private String name;

	@Column(name = "number_of_columns")
	private int numberOfColumns;

	@Column(name = "number_of_rows")
	private int numberOfRows;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cinema_id")
	private Cinema cinema;

	public Room() {}

	public Room(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Room(String name, int numberOfColumns, int numberOfRows, Cinema cinema) {
		this.name = name;
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		this.cinema = cinema;
	}

	public Room(int id, String name, int numberOfColumns, int numberOfRows, Cinema cinema) {
		this.id = id;
		this.name = name;
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		this.cinema = cinema;
	}
}