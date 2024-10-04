package com.example.backendkino.repository;

import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowingRepository extends JpaRepository<Showing, String> {
}
