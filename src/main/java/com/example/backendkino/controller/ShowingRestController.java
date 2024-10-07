package com.example.backendkino.controller;

import com.example.backendkino.model.Admin;
import com.example.backendkino.model.Movie;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.repository.ShowingRepository;
import com.example.backendkino.repository.TheatreRepository;
import com.example.backendkino.service.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
public class ShowingRestController {

    @Autowired
    ShowingService showingService;

    @Autowired
    ShowingRepository showingRepository;
    @PostMapping("/showing/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Showing createShowing(@RequestParam Movie movie, @RequestParam Admin admin, @RequestParam LocalDateTime localDateTime,@RequestParam  Theatre theatre) {
        return showingService.createShowing(theatre, movie, localDateTime, admin);
    }

    @DeleteMapping("/showing/delete/{id}")
    public ResponseEntity<String> deleteShowing(@PathVariable String id){
        if (!showingRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Showing not found\"}");
        }
        showingRepository.deleteById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"Movie deleted successfully\"}");
    }
    }

