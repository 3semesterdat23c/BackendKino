package com.example.backendkino.repository;

import com.example.backendkino.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, String> {
    Genre findByGenreName(String genreName);
}
