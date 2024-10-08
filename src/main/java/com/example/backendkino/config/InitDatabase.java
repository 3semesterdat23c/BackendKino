package com.example.backendkino.config;

import com.example.backendkino.model.Admin;
import com.example.backendkino.model.Movie;
import com.example.backendkino.model.Showing;
import com.example.backendkino.repository.AdminRepository;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.repository.ShowingRepository;
import com.example.backendkino.repository.TheatreRepository;
import com.example.backendkino.service.ApiServiceGetMovies;
import com.example.backendkino.service.ShowingServiceimpl;
import com.example.backendkino.service.TheatreService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class InitDatabase {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private ShowingServiceimpl showingServiceimpl;

    @Autowired
    private ApiServiceGetMovies apiServiceGetMovies;

    @PostConstruct
    public void init() {
        //Fetch movies from OMDB if none exists in our database
        if (movieRepository.count() == 0) {
            List<Movie> movies = apiServiceGetMovies.getMovies();
            movieRepository.saveAll(movies);
        } else {
            System.out.println("Database already populated with movies.");
        }

        //Create root user if no admin exists
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("root");
            admin.setFullName("root");
            admin.setPassword("root");
            adminRepository.save(admin);
        } else {
            System.out.println("Database already has at least one Admin user.");
        }

        //Create two theatres if none exists
        //Also creates seats
        if (theatreRepository.count() == 0) {
            theatreService.createTheatre(20, 12);
            theatreService.createTheatre(25, 16);
        } else {
            System.out.println("Database already has at least one theatre.");
        }

        //Create two showings, one for each theatre if none exists
        if (showingRepository.count() == 0) {
            Showing showing1 = new Showing(
                    1,
                    LocalDateTime.of(2024, 10, 31, 14, 00),
                    theatreRepository.getTheatresByTheatreId(1),
                    movieRepository.findMovieByMovieId(1),
                    adminRepository.findByUsername("root").get(),
                    LocalDateTime.of(2024, 10, 31, 12, 00)
                    );
            showingServiceimpl.createShowing(showing1);

            Showing showing2 = new Showing(
                    2,
                    LocalDateTime.of(2024, 10, 31, 18, 00),
                    theatreRepository.getTheatresByTheatreId(2),
                    movieRepository.findMovieByMovieId(2),
                    adminRepository.findByUsername("root").get(),
                    LocalDateTime.of(2024, 10, 30, 16, 30)
            );
            showingServiceimpl.createShowing(showing2);
        } else {
            System.out.println("Database already has at least one showing.");
        }
    }
}