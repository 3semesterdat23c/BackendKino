package com.example.backendkino.service;

import com.example.backendkino.model.Theatre;
import com.example.backendkino.model.Seat;
import com.example.backendkino.repository.TheatreRepository;
import com.example.backendkino.repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private SeatRepository seatRepository;

    public Theatre createTheatre(int seatRows, int seatsPerRow) {
        Theatre theatre = new Theatre();
        theatre.setSeatRows(seatRows);
        theatre.setSeatsPerRow(seatsPerRow);
        Theatre savedTheatre = theatreRepository.save(theatre);


        for (int row = 1; row <= seatRows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                Seat seat = new Seat();
                seat.setRowNumber(row);
                seat.setSeatNumber(seatNum);
                seat.setTheatre(savedTheatre);
                seatRepository.save(seat);
            }
        }

        return savedTheatre;
    }

    public Theatre findTheatreById(Integer theatreId) {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new EntityNotFoundException("Theatre not found with ID: " + theatreId));
    }
}