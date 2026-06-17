package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private int id;

	@Column(name = "ticket_uid")
	private String uid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "showtime_id")
	private ShowTime showTime;

	@Column(name = "ticket_seats")
	private String seats;

	@Column(name = "ticket_price")
	private BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "ticket_status")
	private TicketStatus status;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	public Ticket() {}

	public Ticket(int id, String uid, User user, ShowTime showTime, String seats, BigDecimal totalPrice,
	              PaymentMethod paymentMethod, TicketStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.uid = uid;
		this.user = user;
		this.showTime = showTime;
		this.seats = seats;
		this.totalPrice = totalPrice;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAtAsDate() {
		return Timestamp.valueOf(createdAt);
	}

	public Date getUpdatedAtAsDate() {
		return Timestamp.valueOf(updatedAt);
	}
}