package com.example.backendkino.repository;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    Set<Seat> findByTheatre(Theatre theatre);
    Set<Seat> getSeatsByBookings(Set<Booking> bookings);
    List<Seat> findAllBySeatIdIn(List<Integer> seatIds);
}
