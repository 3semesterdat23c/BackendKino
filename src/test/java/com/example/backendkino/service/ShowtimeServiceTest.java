package com.example.backendkino.service;

import com.example.backendkino.model.Movie;
import com.example.backendkino.model.Showing;
import com.example.backendkino.model.Theatre;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.repository.ShowingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowtimeServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ShowingRepository showingRepository;

    @InjectMocks
    private ApiServiceShowingImpl showtimeService;

    @Test
    public void testIsShowtimeValid_NoOverlap() {
        // Arrange
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setRuntime("120 minutes");

        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);

        Showing newShowtime = new Showing();
        newShowtime.setMovie(movie);
        newShowtime.setTheatre(theatre);
        newShowtime.setDateTime(LocalDateTime.of(2023, 10, 10, 15, 0));
        newShowtime.setEndTime(LocalDateTime.of(2023, 10, 10, 17, 0));

        List<Showing> existingShowtimes = new ArrayList<>();

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(showingRepository.findByTheatreAndDateTime(theatre, newShowtime.getDateTime()))
                .thenReturn(existingShowtimes);

        // Act
        boolean result = showtimeService.isShowtimeValid(newShowtime);

        // Assert
        assertTrue(result);
    }}
