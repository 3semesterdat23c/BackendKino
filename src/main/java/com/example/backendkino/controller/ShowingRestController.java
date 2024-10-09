package com.example.backendkino.controller;

import com.example.backendkino.model.Admin;
import com.example.backendkino.model.Movie;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.AdminRepository;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.repository.ShowingRepository;
import com.example.backendkino.repository.TheatreRepository;
import com.example.backendkino.service.ShowingServiceimpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/showing")

public class ShowingRestController {

    @Autowired
    ShowingServiceimpl showingService;

    @Autowired
    ShowingRepository showingRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/showings/{movieId}")
    public List<Showing> getShowingsByMovieId(@PathVariable int movieId) {
        return showingService.getShowTimesByMovieId(movieId); // Implement service to fetch showings
    }

    @GetMapping("/theatres")
    public List<Theatre> getAllTheatres(){
        return theatreRepository.findAll();
    }

    @GetMapping("/admins")
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Showing> createShowing(@RequestBody Showing showtime) {
        // Fetch Movie by movieId from the showtime object
        Movie movie = movieRepository.findMovieByMovieId(showtime.getMovie().getMovieId());
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if movie not found
        }

        // Fetch Admin by adminId from the showtime object
        Admin admin = adminRepository.findById(showtime.getAdmin().getAdminId()).orElse(null);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if admin not found
        }

        // Fetch Theatre by theatreId from the showtime object
        Theatre theatre = theatreRepository.findById(showtime.getTheatre().getTheatreId()).orElse(null);
        if (theatre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if theatre not found
        }

        // Validate the showtime (overlaps, etc.)
        if (!showingService.isShowtimeValid(showtime)) {
            return ResponseEntity.badRequest().body(null); // Return 400 if the showtime is invalid
        }

        // Call the service to create the showing
        Showing createdShowtime = showingService.createShowing(showtime);

        return new ResponseEntity<>(createdShowtime, HttpStatus.CREATED); // Return 201 with the created showing
    }


    @DeleteMapping("/showing/delete/{id}")
    public ResponseEntity<String> deleteShowing(@PathVariable int id) {
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





