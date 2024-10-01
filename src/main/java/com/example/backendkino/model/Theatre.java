package com.example.backendkino.model;

import jakarta.persistence.*;

@Entity
@Table(name = "theatre")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theatre_id")
    private int theatreId;

    @Column(name = "seatRows", nullable = false)
    private int seatRows;

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public int getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    @Column(name = "seatsPerRow", nullable = false)
    private int seatsPerRow;
}
