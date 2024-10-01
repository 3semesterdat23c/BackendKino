package com.example.backendkino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Showing {

    @Id
    private int showingId;

    private int theatreId;
    private int movieId;
    private int adminId;
    private LocalDateTime dateTime;
}
