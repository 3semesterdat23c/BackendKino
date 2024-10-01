package com.example.backendkino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Genre {

    @Id
    private int genreId;

    private String genreName;
}
