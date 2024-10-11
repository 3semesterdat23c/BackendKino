package com.example.backendkino.repository;

import com.example.backendkino.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
}
