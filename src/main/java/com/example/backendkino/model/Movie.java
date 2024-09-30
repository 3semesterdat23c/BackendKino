package com.example.backendkino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class Movie {

    @Id
    private int movieId;

    private String description;
    private int length;
    private double rating;
    private int ticketPrice;
    private int ageRestriction;
    private String poster;
}
