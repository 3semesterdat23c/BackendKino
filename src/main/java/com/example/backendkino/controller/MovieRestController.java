package com.example.backendkino.controller;


import com.example.backendkino.model.Movie;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.service.ApiServiceGetMovies;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
/*
*    @GetMapping("/kommune/{kode}")
    public ResponseEntity<Kommune> getKommuneByKode(@PathVariable String kode) {
        Optional<Kommune> kommuneOptional = kommuneRepository.findById(kode);
        return kommuneOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/
}
