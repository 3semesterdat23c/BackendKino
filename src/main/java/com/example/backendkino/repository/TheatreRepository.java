package com.example.backendkino.repository;

import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TheatreRepository extends JpaRepository<Theatre, String> {
    Theatre getTheatresByTheatreId(int theatreId);
}