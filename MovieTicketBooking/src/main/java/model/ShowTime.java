package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "showtimes")
@SQLDelete(sql = "UPDATE showtimes SET deleted_at = NOW() WHERE showtime_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class ShowTime extends AbsBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "showtime_id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cinema_id")
	private Cinema cinema;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@Column(name = "showtime_price")
	private BigDecimal pricePerTicket;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	public ShowTime() {}

	public ShowTime(Cinema cinema, Room room, Movie movie, BigDecimal pricePerTicket,
	                LocalDateTime startTime) {
		this.cinema = cinema;
		this.room = room;
		this.movie = movie;
		this.pricePerTicket = pricePerTicket;
		this.startTime = startTime;
		this.createdAt = LocalDateTime.now();
	}

	public ShowTime(int id, Cinema cinema, Room room, Movie movie, BigDecimal pricePerTicket,
	                LocalDateTime startTime, LocalDateTime createdAt) {
		this.id = id;
		this.cinema = cinema;
		this.room = room;
		this.movie = movie;
		this.pricePerTicket = pricePerTicket;
		this.startTime = startTime;
		this.createdAt = createdAt;
	}

	public List<ShowTimeSeat> createListShowTimeSeats(){
		int rows = room.getNumberOfRows();
		int columns = room.getNumberOfColumns();
		if (rows < 1 && rows > 26)
			throw new IllegalArgumentException("Number of row must be in 1 and 26 ");
		List<ShowTimeSeat> seats = new ArrayList<>();
		int length = rows * columns;
		int count = 0;
		char seatLetter = 'A';
		int seatNumber = 1;
		while (count < length) {
			seats.add(new ShowTimeSeat("" + seatLetter + seatNumber, room, this));
			seatNumber++;
			count++;
			if (count >= columns && count % columns == 0) {
				seatNumber = 1;
				seatLetter++;
			}
		}
		return seats;
	}

	public int getCinemaId() {
		return cinema.getId();
	}

	public int getRoomId() {
		return room.getId();
	}

	public int getMovieId() {
		return movie.getId();
	}

	public String getCinemaInfo() {
		return cinema.getAllInfo();
	}

	public String getRoomName() {
		return room.getName();
	}

	public String getMovieName() {
		return movie.getName();
	}

	public Date getStartTimeAsDate() {
		return Timestamp.valueOf(this.startTime);
	}

	public Date getCreatedAtAsDate() {
		return Timestamp.valueOf(this.createdAt);
	}
}