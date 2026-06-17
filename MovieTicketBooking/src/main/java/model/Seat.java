package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "row_label", nullable = false)
    private String rowLabel;

    @Column(name = "column_number", nullable = false)
    private int columnNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private SeatType seatType = SeatType.REGULAR;

    public Seat() {}

    public Seat(Room room, String rowLabel, int columnNumber, SeatType seatType) {
        this.room = room;
        this.rowLabel = rowLabel;
        this.columnNumber = columnNumber;
        this.seatType = seatType;
    }
}