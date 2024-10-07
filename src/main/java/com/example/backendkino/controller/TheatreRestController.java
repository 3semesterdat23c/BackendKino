package com.example.backendkino.controller;

import com.example.backendkino.model.Theatre;
import com.example.backendkino.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theatre")
@CrossOrigin
public class TheatreRestController {

    @Autowired
    private TheatreService theatreService;

    @PostMapping("/create")
    public ResponseEntity<?> createTheatre(@RequestParam int seatRows, @RequestParam int seatsPerRow) {
        try {

            Theatre createdTheatre = theatreService.createTheatre(seatRows, seatsPerRow);


            return ResponseEntity.status(HttpStatus.CREATED).body(createdTheatre);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the theatre.");
        }
    }
}
