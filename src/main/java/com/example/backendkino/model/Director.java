package com.example.backendkino.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private int directorId;

    @Column(name = "fullName")
    private String fullName;


    public Director(String fullName) {
        this.fullName = fullName;
    }



    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
