package com.example.backendkino.repository;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
Booking findAllByShowing(Showing showing);
}
