package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_audit_logs")
@Getter
@Setter
public class BookingAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public BookingAuditLog() {}

    public BookingAuditLog(Booking booking, User actor, String actionType, String oldValue, String newValue, String notes) {
        this.booking = booking;
        this.actor = actor;
        this.actionType = actionType;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
    }
}