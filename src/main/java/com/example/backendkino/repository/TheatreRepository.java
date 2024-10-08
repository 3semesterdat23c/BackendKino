package com.example.backendkino.repository;

import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
    Optional<Theatre> findById(Integer theatreId);
    Theatre getTheatresByTheatreId(int theatreId);
}
