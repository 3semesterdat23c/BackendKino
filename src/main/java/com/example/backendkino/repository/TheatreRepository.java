package com.example.backendkino.repository;

import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
    Optional<Theatre> findById(Integer theatreId);
    Theatre getTheatresByTheatreId(int theatreId);
}
