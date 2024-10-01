package com.example.backendkino.controller;


import com.example.backendkino.model.Movie;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.service.ApiServiceGetMovies;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
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
    @GetMapping ("/movie/{id}")
    public ResponseEntity<Movie> getMovieById (@PathVariable String id){
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping ("/movie/{id}")
    public ResponseEntity<Movie> updateMovie (@PathVariable String id, @RequestBody Movie movie){
        if (!movieRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        movie.setId(Integer.valueOf(id));
        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);

    }


/*

        kommune.setKode(kode);
        Kommune updatedKommune = kommuneRepository.save(kommune);
        return ResponseEntity.ok(updatedKommune);
    }*/


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
