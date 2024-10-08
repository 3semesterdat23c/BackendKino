package com.example.backendkino.controller;

import com.example.backendkino.model.*;
import com.example.backendkino.repository.*;
import com.example.backendkino.service.SeatService;
import com.example.backendkino.service.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
public class ShowingRestController {

    @Autowired
    ShowingService showingService;

    @Autowired
    ShowingRepository showingRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private SeatService seatService;

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

    // Hent ledige sæder for en bestemt visning
    @GetMapping("/{showingId}/seats")
    public ResponseEntity<Set<Seat>> getAvailableSeats(@PathVariable int showingId, @RequestParam int theaterId) {
        Set<Seat> availableSeats = seatService.getAvailableSeatsInShowing(showingId, theaterId);
        return ResponseEntity.ok(availableSeats);
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
