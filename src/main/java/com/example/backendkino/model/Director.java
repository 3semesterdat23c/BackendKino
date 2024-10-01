package com.example.backendkino.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private int directorId;

    @Column(name = "fullName")
    private String fullName;

    @ManyToMany(mappedBy = "directors", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    private Set<Movie> movies;

}
