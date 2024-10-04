package com.example.backendkino.service;

import com.example.backendkino.model.Admin;
import com.example.backendkino.model.Movie;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.AdminRepository;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.repository.ShowingRepository;
import com.example.backendkino.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ShowingService {

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Showing createShowing(Theatre theatre, Movie movie, LocalDateTime localDateTime, Admin admin){
        Showing showing = new Showing();
        showing.setMovie(movie);
        showing.setAdmin(admin);
        showing.setTheatre(theatre);
        showing.setDateTime(localDateTime);
        Showing savedShowing = showingRepository.save(showing);
    return showing;
    }


}
