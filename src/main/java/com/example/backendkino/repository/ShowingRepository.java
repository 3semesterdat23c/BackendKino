package com.example.backendkino.repository;

import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ShowingRepository extends JpaRepository<Showing, Integer> {

    // Find showtimes for a specific theatre and within a date range
    List<Showing> findByTheatreAndDateTimeBetween(Theatre theatre, LocalDateTime startDate, LocalDateTime endDate);

    // Find showtimes by movie ID (this method should exist as well)
    List<Showing> findShowingByMovie_MovieId(int movieId);
}