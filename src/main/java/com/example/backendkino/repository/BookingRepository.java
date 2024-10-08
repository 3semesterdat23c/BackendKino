package com.example.backendkino.repository;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
Booking findAllByShowing(Showing showing);
Set<Booking> getBookingByShowing(Showing showing);
}
