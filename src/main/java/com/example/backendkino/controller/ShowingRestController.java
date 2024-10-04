package com.example.backendkino.controller;

import com.example.backendkino.model.Admin;
import com.example.backendkino.model.Movie;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.repository.TheatreRepository;
import com.example.backendkino.service.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
public class ShowingRestController {

    @Autowired
    ShowingService showingService;

    @PostMapping("/showing/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Showing createShowing(@RequestParam Movie movie, @RequestParam Admin admin, @RequestParam LocalDateTime localDateTime,@RequestParam  Theatre theatre) {
        return showingService.createShowing(theatre, movie, localDateTime, admin);
    }
}
