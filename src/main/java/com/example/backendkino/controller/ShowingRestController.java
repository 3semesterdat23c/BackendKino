package com.example.backendkino.controller;

import com.example.backendkino.model.Admin;
import com.example.backendkino.model.Movie;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.repository.TheatreRepository;
import com.example.backendkino.service.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
public class ShowingRestController {

    @Autowired
    ShowingService showingService;

    @PostMapping("/showing/create")
    public Showing createShowing(@RequestParam Movie movie, @RequestParam Admin admin, LocalDateTime localDateTime, Theatre theatre) {
        return showingService.createShowing(theatre, movie, localDateTime, admin);
    }
}
