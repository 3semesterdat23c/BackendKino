package com.example.backendkino.controller;

import com.example.backendkino.model.*;
import com.example.backendkino.repository.*;
import com.example.backendkino.service.BookingService;
import com.example.backendkino.service.SeatService;
import com.example.backendkino.service.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@RestController
public class ShowingRestController {

    @Autowired
    private ShowingService showingService;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private BookingService bookingService;


    @PostMapping("/showing/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Showing createShowing(@RequestParam Movie movie, @RequestParam Admin admin, @RequestParam LocalDateTime localDateTime, @RequestParam Theatre theatre) {
        return showingService.createShowing(theatre, movie, localDateTime, admin);
    }

    @DeleteMapping("/showing/delete/{id}")
    public ResponseEntity<String> deleteShowing(@PathVariable String id) {
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
