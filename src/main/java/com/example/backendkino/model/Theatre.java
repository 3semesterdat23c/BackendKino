package com.example.backendkino.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "theatre")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theatre_id")
    private int theatreId;

    @Column(name = "seatRows", nullable = false)
    private int seatRows;

    @Column(name = "seatsPerRow", nullable = false)
    private int seatsPerRow;


}
