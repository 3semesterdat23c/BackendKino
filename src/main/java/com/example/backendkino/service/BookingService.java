package com.example.backendkino.service;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.BookingRepository;
import com.example.backendkino.repository.SeatRepository;
import com.example.backendkino.repository.ShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private SeatRepository seatRepository;

    public Booking createBooking(String email, Showing showing, Set<Seat> seatsToBeBooked) {
        Booking booking = new Booking();
        booking.setEmail(email);
        booking.setShowing(showing);
        booking.setSeats(seatsToBeBooked);
        return bookingRepository.save(booking);
    }

    public Set<Booking> getAllBookings() {
        return new HashSet<>(bookingRepository.findAll());
    }

    public Booking getBookingById(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking updateBooking(int id, Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        existingBooking.setSeats(updatedBooking.getSeats());
        existingBooking.setShowing(updatedBooking.getShowing());
        existingBooking.setEmail(updatedBooking.getEmail());
        return bookingRepository.save(existingBooking);
    }


    public void deleteBooking(int id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

    public Set<Seat> getBookedSeatsInShowing(int showingId){
        Showing currentShowing = showingRepository.getShowingsByShowingId(showingId);
        Set<Booking> bookingsInCurrentShowing = bookingRepository.getBookingByShowing(currentShowing);
        Set<Seat> bookedSeats = seatRepository.getSeatsByBookings(bookingsInCurrentShowing);
        return bookedSeats;
    }

    public Set<Seat> getAvailableSeatsInShowing(int showingId, Theatre theatre){
        Set<Seat> seatsInTheater = seatRepository.getSeatsByTheatre(theatre);
        seatsInTheater.removeAll(getBookedSeatsInShowing(showingId));
        return seatsInTheater;
    }
}
