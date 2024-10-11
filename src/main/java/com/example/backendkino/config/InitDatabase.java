package com.example.backendkino.config;

import com.example.backendkino.model.*;
import com.example.backendkino.repository.*;
import com.example.backendkino.service.ApiServiceGetMovies;
import com.example.backendkino.service.BookingService;
import com.example.backendkino.service.ShowingServiceimpl;
import com.example.backendkino.service.TheatreService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private BookingService bookingService;

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
            Admin admin1 = new Admin();
            admin1.setUsername("Bob123");
            admin1.setFullName("Bobby Sørensen");
            admin1.setPassword("bob");
            adminRepository.save(admin1);
            Admin admin2 = new Admin();
            admin2.setUsername("JohnErSej");
            admin2.setFullName("John Johnson");
            admin2.setPassword("john");
            adminRepository.save(admin2);
        } else {
            System.out.println("Database already has at least one Admin user.");
        }

        //Create two theatres if none exists
        //Also creates seats
        if (theatreRepository.count() == 0) {
            theatreService.createTheatre(10, 10);
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

            Showing showing3 = new Showing(
                    3,
                    LocalDateTime.of(2024, 11, 01, 11, 00),
                    theatreRepository.getTheatresByTheatreId(2),
                    movieRepository.findMovieByMovieId(7),
                    adminRepository.findByUsername("root").get(),
                    LocalDateTime.of(2024, 10, 30, 14, 14)
            );
            showingServiceimpl.createShowing(showing3);
            Showing showing4 = new Showing(
                    4,
                    LocalDateTime.of(2024, 10, 29, 10, 00),
                    theatreRepository.getTheatresByTheatreId(1),
                    movieRepository.findMovieByMovieId(1),
                    adminRepository.findByUsername("root").get(),
                    LocalDateTime.of(2024, 10, 29, 12, 40)
            );
            showingServiceimpl.createShowing(showing4);
            Showing showing5 = new Showing(
                    5,
                    LocalDateTime.of(2024, 11, 03, 11, 00),
                    theatreRepository.getTheatresByTheatreId(2),
                    movieRepository.findMovieByMovieId(1),
                    adminRepository.findByUsername("bob123").get(),
                    LocalDateTime.of(2024, 11, 03, 13, 40)
            );
            showingServiceimpl.createShowing(showing5);
        } else {
            System.out.println("Database already has at least one showing.");
        }

        /*
        if (bookingRepository.count() == 0) {
            Showing showing = showingRepository.getShowingByShowingId(1);
            Seat seat1 = seatRepository.getSeatsBySeatId(1);
            Seat seat2 = seatRepository.getSeatsBySeatId(2);
            Seat seat3 = seatRepository.getSeatsBySeatId(3);
            Seat seat4 = seatRepository.getSeatsBySeatId(4);

            // Explicitly save or re-fetch the seats to ensure they are attached to the current session
            Set<Seat> bookedSeats = Set.of(seat1,seat2,seat3,seat4);

            // Save both bookings to the repository using managed seats
            bookingService.createBooking("tislård@gmail.com", showing, bookedSeats);
        } else {
            System.out.println("Database already has at least one booking.");
        }

    */

    }
}