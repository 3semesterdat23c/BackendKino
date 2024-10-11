package com.example.backendkino.repository;

import com.example.backendkino.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    boolean existsByImdbID(String imdbId);
    List<Movie> findByTitleContaining(String string);
    Movie findMovieByMovieId(int movieId);

}
