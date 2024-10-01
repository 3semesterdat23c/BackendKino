package com.example.backendkino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Booking {

    @Id
    private int bookingId;

    private int showingId;
    private String email;
}
