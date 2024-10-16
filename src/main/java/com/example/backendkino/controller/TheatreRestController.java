package com.example.backendkino.controller;

import com.example.backendkino.model.Theatre;
import com.example.backendkino.service.ApiServiceTheatreImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theatre")
@CrossOrigin
public class TheatreRestController {

    @Autowired
    private ApiServiceTheatreImpl apiServiceTheatreImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createTheatre(@RequestBody Theatre theatre) {
        try {

            Theatre createdTheatre = apiServiceTheatreImpl.createTheatre(theatre.getSeatRows(), theatre.getSeatsPerRow());


            return ResponseEntity.status(HttpStatus.CREATED).body(createdTheatre);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the theatre.");
        }
    }
}
