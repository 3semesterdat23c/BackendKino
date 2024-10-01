package com.example.backendkino.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name ="seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int seatId;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Column(name = "rowNumber", nullable = false)
    private int rowNumber;

    @ManyToMany(mappedBy = "seats", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    private Set<Booking> bookings;
}
