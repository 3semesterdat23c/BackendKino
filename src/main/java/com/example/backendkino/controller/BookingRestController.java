package com.example.backendkino.controller;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Showing;
import com.example.backendkino.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingRestController {

    @Autowired
    BookingService bookingService;@RestController
    @RequestMapping("/booking")
    @CrossOrigin
    public class BookingRestController {

        @Autowired
        private BookingService bookingService;

        @PostMapping
        public ResponseEntity<?> createBooking(
                @RequestParam Set<Seat> seatsToBeBooked,
                @RequestBody Showing showing,
                @RequestParam String email) {

            try {

                Booking newBooking = bookingService.createBooking(email, showing, seatsToBeBooked);

                return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);

            } catch (IllegalArgumentException e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (Exception e) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the booking.");
            }
        }
    }


    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Set<Seat> seatsToBeBooked, @RequestBody Showing showing, @RequestParam String email){

    }

}
