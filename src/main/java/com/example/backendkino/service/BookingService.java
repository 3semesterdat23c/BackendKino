package com.example.backendkino.service;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Showing;
import com.example.backendkino.repository.BookingRepository;
import com.example.backendkino.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;



    public Booking createBooking(String email, Showing showing, Set<Seat> seats) {
        if (email == null || showing == null || seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: email, showing, and seats must not be null, and seats must not be empty.");
        }
        Set<Seat> alreadyBookedSeats = bookingRepository.findAllByShowing(showing).getSeats();

        for (Seat seat : seats) {
            if (alreadyBookedSeats.contains(seat)) {
                throw new IllegalArgumentException("One or more seats are already booked.");
            }
        }


        Booking newBooking = new Booking(seats, showing, email);


        bookingRepository.save(newBooking);

        return newBooking;
    }


}
