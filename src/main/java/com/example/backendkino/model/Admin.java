package com.example.backendkino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Admin {

    @Id
    private int adminId;

    private String username;
    private String password;
    private String fullName;
}
