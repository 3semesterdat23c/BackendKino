package com.example.backendkino.model;

import com.example.backendkino.service.BookingService;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(
            name = "booking_seat", // Join table name
            joinColumns = @JoinColumn(name = "booking_id"), // Foreign key to Booking
            inverseJoinColumns = @JoinColumn(name = "seat_id") // Foreign key to Seat
    )
    private Set<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "showing_id", nullable = false)
    private Showing showing;

    @Column(name = "email", nullable = false)
    private String email;

    public Booking(Set<Seat> seats, Showing showing, String email) {
        this.seats = seats;
        this.showing = showing;
        this.email = email;
    }

    public Booking(){}

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Showing getShowing() {
        return showing;
    }

    public void setShowing(Showing showing) {
        this.showing = showing;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
