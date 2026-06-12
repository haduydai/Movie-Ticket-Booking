package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "showtimeseats")
@Getter
@Setter
public class ShowTimeSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "showtimeseat_id")
	private int id;

	@Column(name = "seat_name")
	private String seatName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User bookedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "showtime_id")
	private ShowTime showTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	public ShowTimeSeat() {}

	public ShowTimeSeat(int id, String seatName, Room room, User bookedBy, ShowTime showTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.seatName = seatName;
		this.room = room;
		this.bookedBy = bookedBy;
		this.showTime = showTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ShowTimeSeat(String seatName, Room room, User bookedBy, ShowTime showTime) {
		this.seatName = seatName;
		this.room = room;
		this.bookedBy = bookedBy;
		this.showTime = showTime;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = createdAt;
	}

	public ShowTimeSeat(String seatName, Room room, ShowTime showTime) {
		this.seatName = seatName;
		this.room = room;
		this.bookedBy = null;
		this.showTime = showTime;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = createdAt;
	}

	public int getUserId() {
		return bookedBy != null ? bookedBy.getId() : 0;
	}

	public int getShowTimeId() {
		return showTime.getId();
	}
}