package com.example.backendkino.repository;

import com.example.backendkino.model.Booking;
import com.example.backendkino.model.Seat;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ShowingRepository extends JpaRepository<Showing, String> {
Showing getShowingsByShowingId(int showingId);
}
