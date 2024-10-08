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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ShowingServiceimpl implements ApiServicegetShowing {

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AdminRepository adminRepository;



    @Override
    public Showing createShowing(Showing showing) {
        // Hent film og teater fra databasen baseret på de sendte ID'er
        Movie movie = movieRepository.findById(showing.getMovie().getMovieId()).orElseThrow(
                () -> new RuntimeException("Movie not found"));
        Theatre theater = theatreRepository.findById(showing.getTheatre().getTheatreId()).orElseThrow(
                () -> new RuntimeException("Theater not found"));
        Admin admin = adminRepository.findById(showing.getAdmin().getAdminId()).orElseThrow(
                () -> new RuntimeException("Admin not found")
        );
        // Sæt de hentede film og teater ind i showtime
        showing.setMovie(movie);
        showing.setTheatre(theater);
        showing.setAdmin(admin);

        // Gem showtime
        return showingRepository.save(showing);
    }

    public List<Showing> getShowTimesByMovieId(int movieId) {
        return showingRepository.findShowingByMovie_MovieId(movieId);
    }

    public boolean isShowtimeValid(Showing newShowtime) {
        // Assuming that you want to check for showtimes on the same date and that the new showtime has a valid end time
        LocalDateTime startDateTime = newShowtime.getDateTime();
        LocalDateTime endDateTime = newShowtime.getEndTime(); // Ensure endTime is set appropriately

        List<Showing> existingShowtimes = showingRepository.findByTheatreAndDateTimeBetween(
                newShowtime.getTheatre(),
                startDateTime,
                endDateTime
        );

        for (Showing eShowtime : existingShowtimes) {
            LocalDateTime cleaning = eShowtime.getEndTime().plusMinutes(30);

            // Check for overlaps
            if (startDateTime.isBefore(cleaning) && endDateTime.isAfter(eShowtime.getDateTime())) {
                return false; // Overlap detected
            }
        }
        return true; // No overlap
    }


//    public Showing createShowing(Theatre theatre, Movie movie, LocalDateTime startTime, Admin admin, LocalDateTime endTime) {
//        Showing showing = new Showing();
//        showing.setTheatre(theatre);
//        showing.setMovie(movie);
//        showing.setDateTime(startTime);
//        showing.setEndTime(endTime); // Set the end time as well
//
//        return showingRepository.save(showing);
//    }
//
//
//    // Fetch all showings for a specific movie by movieId
//    public List<Showing> getShowingsByMovieId(int movieId) {
//        // Assuming your ShowingRepository has a method to find showings by movie ID
//        return showingRepository.findByMovie_MovieId(movieId);
//
//    }
}
