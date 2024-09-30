package com.example.backendkino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Theatre {

    @Id
    private int theatreId;

    private int seatRows;
    private int seatsPerRow;
}
