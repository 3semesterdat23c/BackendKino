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
import java.util.List;

@Service
public class ApiServiceShowingImpl implements ApiServicegetShowing {

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

        LocalDateTime endTime;
        String[] runTime = movie.getRuntime().split(" ");
        int runTimeMinutes =  Integer.valueOf(runTime[0]);
        endTime = showing.getDateTime().plusHours(1);
        endTime = endTime.plusMinutes(runTimeMinutes);
        showing.setEndTime(endTime);

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
        Movie movie = movieRepository.findById(newShowtime.getMovie().getMovieId()).orElseThrow(
                () -> new RuntimeException("Movie not found"));

        List<Showing> existingShowtimes = showingRepository.findByTheatreAndDateTime(
                newShowtime.getTheatre(),
                startDateTime

        );
        String[] runTime =movie.getRuntime().split(" ");
        int runTimeMinutes =  Integer.valueOf(runTime[0]);
        for (Showing eShowtime : existingShowtimes) {
            LocalDateTime cleaning = eShowtime.getEndTime().plusMinutes(60+runTimeMinutes);

            // Check for overlaps
            if (startDateTime.isBefore(cleaning)) {
                return false;
            }
        }
        return true;
    }

}
