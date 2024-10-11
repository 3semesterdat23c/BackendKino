package com.example.backendkino.repository;

import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;


public interface ShowingRepository extends JpaRepository<Showing, Integer> {

    List<Showing> findByTheatreAndDateTime(Theatre theatre, LocalDateTime startDate);

    List<Showing> findShowingByMovie_MovieId(int movieId);
    Showing getShowingByShowingId(int showingId);

}