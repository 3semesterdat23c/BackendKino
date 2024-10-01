package com.example.backendkino.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "showing")
public class Showing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showing_id")
    private int showingId;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;
}
