package com.example.backendkino.controller;


import com.example.backendkino.model.Movie;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.service.ApiServiceGetMovies;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieRestController {

    @Autowired
    private ApiServiceGetMovies apiServiceGetMovies;
    @Autowired
    private MovieRepository movieRepository;
    @GetMapping("/getMovies")
    public List<Movie> getMovies() {
        return apiServiceGetMovies.getMovies(); // Call the method from the Test interface
    }
    @GetMapping ("/movies")
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }


    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable String id) {
        System.out.println("Attempting to delete movie with ID: " + id);
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Movie not found\"}");
        }
        movieRepository.deleteById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"Movie deleted successfully\"}");
    }



}
