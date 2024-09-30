package com.example.backendkino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Seat {

    @Id
    private int seatId;

    private int theatreId;
    private int seatNumber;
}
