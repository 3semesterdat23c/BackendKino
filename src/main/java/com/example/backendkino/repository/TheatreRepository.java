package com.example.backendkino.repository;

import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, String> {
}
