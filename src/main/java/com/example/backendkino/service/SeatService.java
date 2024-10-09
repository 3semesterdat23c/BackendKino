package com.example.backendkino.service;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.BookingRepository;
import com.example.backendkino.repository.SeatRepository;
import com.example.backendkino.repository.ShowingRepository;
import com.example.backendkino.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SeatService {
    private final ShowingRepository showingRepository;
    private SeatRepository seatRepository;
    private TheatreRepository theatreRepository;
    private BookingRepository bookingRepository;

    public SeatService(ShowingRepository showingRepository) {
        this.showingRepository = showingRepository;
    }

    public Set<Seat> getSeatsFromTheater(int theaterId){
        Set<Seat> seatsInTheater = seatRepository.findByTheatre(theatreRepository.getTheatresByTheatreId(theaterId));

        return seatsInTheater;
    }

    public Set<Seat> getBookedSeatsInShowing(int showingId){
        Showing currentShowing = showingRepository.getShowingByShowingId(showingId);
        Set<Booking> bookingsInCurrentShowing = bookingRepository.getBookingByShowing(currentShowing);
        Set<Seat> bookedSeats = seatRepository.getSeatsByBookings(bookingsInCurrentShowing);
        return bookedSeats;
    }

}
