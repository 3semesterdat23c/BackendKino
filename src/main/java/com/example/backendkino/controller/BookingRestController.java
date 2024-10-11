package com.example.backendkino.controller;

import com.example.backendkino.model.*;
import com.example.backendkino.service.ApiBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
public class BookingRestController {

    @Autowired
    private ApiBookingServiceImpl apiBookingServiceImpl;

    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestParam Set<Seat> seatsToBeBooked,
            @RequestBody Showing showing,
            @RequestParam String email) {

        try {
            Booking newBooking = apiBookingServiceImpl.createBooking(email, showing, seatsToBeBooked);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the booking.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        try {
            Set<Booking> bookings = apiBookingServiceImpl.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the bookings.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable int id) {
        try {
            Booking booking = apiBookingServiceImpl.getBookingById(id);
            if (booking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
            }
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the booking.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(
            @PathVariable int id,
            @RequestBody Booking updatedBooking) {
        try {
            Booking updated = apiBookingServiceImpl.updateBooking(id, updatedBooking);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the booking.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable int id) {
        try {
            apiBookingServiceImpl.deleteBooking(id);
            return ResponseEntity.ok("Booking deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the booking.");
        }
    }

}
