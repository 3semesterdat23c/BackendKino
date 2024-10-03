package com.example.backendkino.controller;

import com.example.backendkino.model.Theatre;
import com.example.backendkino.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theatre")
@CrossOrigin
public class TheatreRestController {

    @Autowired
    private TheatreService theatreService;

    @PostMapping("/create")
    public Theatre createTheatre(@RequestParam int seatRows, @RequestParam int seatsPerRow) {
        return theatreService.createTheatre(seatRows, seatsPerRow);
    }
}
