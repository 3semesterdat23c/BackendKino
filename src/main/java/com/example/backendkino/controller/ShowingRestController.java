package com.example.backendkino.controller;

import com.example.backendkino.model.*;
import com.example.backendkino.repository.*;
import com.example.backendkino.service.BookingService;
import com.example.backendkino.service.SeatService;
import com.example.backendkino.service.ShowingService;
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
import java.util.*;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/showing")

public class ShowingRestController {

    @Autowired
    private ShowingService showingService;

    @Autowired
    private ShowingRepository showingRepository;
    ShowingServiceimpl showingService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;


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


        @GetMapping("/{showingId}/seats")
        public ResponseEntity<Map<String, Set<Seat>>> getSeatsForShowing(@PathVariable int showingId, @PathVariable Theatre theatre) {
            Set<Seat> bookedSeats = bookingService.getBookedSeatsInShowing(showingId);
            Set<Seat> availableSeats = bookingService.getAvailableSeatsInShowing(showingId, theatre);

            Map<String, Set<Seat>> response = new HashMap<>();
            response.put("bookedSeats", bookedSeats);
            response.put("availableSeats", availableSeats);

            return ResponseEntity.ok(response);
        }


    // Gem en booking
    @PostMapping("/booking")
    public ResponseEntity<String> saveBooking(
            @RequestParam int showingId,
            @RequestParam String email,
            @RequestBody List<Integer> seatIds) {

        // Find visningen (showing) baseret på showingId
        Showing showing = showingRepository.getShowingsByShowingId(showingId);

        // Find sæder baseret på seatIds
        List<Seat> selectedSeats = seatRepository.findAllById(seatIds);

        if (selectedSeats.size() != seatIds.size()) {
            return ResponseEntity.badRequest().body("One or more seats are invalid");
        }

        // Opret en ny booking med de valgte sæder og brugerens email
        Booking newBooking = new Booking(new HashSet<>(selectedSeats), showing, email);

        // Gem bookingen i databasen
        bookingRepository.save(newBooking);

        return ResponseEntity.ok("Booking gemt");
    }
}





