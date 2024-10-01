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

    public int getShowingId() {
        return showingId;
    }

    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;
}
