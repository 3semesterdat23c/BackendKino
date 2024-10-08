package com.example.backendkino.config;

import com.example.backendkino.model.Admin;
import com.example.backendkino.model.Movie;
import com.example.backendkino.repository.AdminRepository;
import com.example.backendkino.repository.MovieRepository;
import com.example.backendkino.service.ApiServiceGetMovies;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitDatabase {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ApiServiceGetMovies apiServiceGetMovies;

    @PostConstruct
    public void init() {
        if (movieRepository.count() == 0) {
            List<Movie> movies = apiServiceGetMovies.getMovies();
            movieRepository.saveAll(movies);
        } else {
            System.out.println("Database already populated with movies.");
        }
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("root");
            admin.setFullName("root");
            admin.setPassword("root");
            adminRepository.save(admin);
        } else {
            System.out.println("Root user already exists.");
        }
    }
}