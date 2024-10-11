package com.example.backendkino.repository;

import com.example.backendkino.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByTitle(String string);
    boolean existsByImdbID(String imdbId);
    Optional<Movie> findByImdbID(String imdbId);
    List<Movie> findByTitleContaining(String string);
    Movie findMovieByMovieId(int movieId);

}
