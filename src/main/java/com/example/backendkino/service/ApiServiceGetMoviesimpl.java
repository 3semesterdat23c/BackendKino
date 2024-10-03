package com.example.backendkino.service;

import com.example.backendkino.model.Actor;
import com.example.backendkino.model.Director;
import com.example.backendkino.model.Genre;
import com.example.backendkino.model.Movie;
import com.example.backendkino.repository.ActorRepository;
import com.example.backendkino.repository.DirectorRepository;
import com.example.backendkino.repository.GenreRepository;
import com.example.backendkino.repository.MovieRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
    @Service
    public class ApiServiceGetMoviesimpl implements ApiServiceGetMovies {

        @Value("${api.key}")
        private String API_KEY; // Make sure your API key is valid

        private static final String BASE_URL = "https://www.omdbapi.com/";

        @Autowired
        private MovieRepository movieRepository; // Autowire your repository

        @Autowired
        private GenreRepository genreRepository;
        @Autowired
        private ActorRepository actorRepository;
        @Autowired
        private DirectorRepository directorRepository;

        @Override
        public List<Movie> getMovies() {
            return fetchMovies(); // Call the method to fetch movies
        }

        private List<Movie> fetchMovies() {
            List<Movie> results = new ArrayList<>();

            for (int page = 1; page <= 5; page++) {
                try {
                    String urlString = BASE_URL + "?s=movie&type=movie&page=" + page + "&apikey=" + API_KEY;
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    if (conn.getResponseCode() == 200) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String inputLine;

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        JSONObject data = new JSONObject(response.toString());

                        if (data.has("Error")) {
                            System.out.println("API Error: " + data.getString("Error"));
                            continue;
                        }

                        if (data.has("Search")) {
                            JSONArray movies = data.getJSONArray("Search");
                            for (int i = 0; i < movies.length(); i++) {
                                JSONObject movieJson = movies.getJSONObject(i);
                                String imdbID = movieJson.getString("imdbID");

                                // Fetch detailed movie information
                                String detailedUrlString = BASE_URL + "?i=" + imdbID + "&apikey=" + API_KEY;
                                URL detailedUrl = new URL(detailedUrlString);
                                HttpURLConnection detailedConn = (HttpURLConnection) detailedUrl.openConnection();
                                detailedConn.setRequestMethod("GET");

                                if (detailedConn.getResponseCode() == 200) {
                                    BufferedReader detailedIn = new BufferedReader(new InputStreamReader(detailedConn.getInputStream()));
                                    StringBuilder detailedResponse = new StringBuilder();
                                    while ((inputLine = detailedIn.readLine()) != null) {
                                        detailedResponse.append(inputLine);
                                    }
                                    detailedIn.close();

                                    // Parse detailed movie information
                                    JSONObject detailedData = new JSONObject(detailedResponse.toString());
                                    Movie movie = new Movie(
                                            null, // ID will be generated by the database
                                            detailedData.getString("Title"),
                                            detailedData.getString("Year"),
                                            detailedData.optString("Released", "N/A"),
                                            detailedData.optString("Runtime", "N/A"),
                                            detailedData.optString("Poster", "N/A"),
                                            detailedData.optString("imdbRating", "N/A"),
                                            detailedData.getString("imdbID")
                                    );

                                    //---------------------------------------------------------------------------
                                    // SET AND SAVE DIRECTORS

                                    String directorString = detailedData.optString("Director", "N/A");
                                    String[] directorArray = directorString.split(",\\s*");

                                    Set<Director> directors = new HashSet<>();

                                    for (String directorName : directorArray) {
                                        Director director = directorRepository.findDirectorByFullName(directorName);

                                        if (director == null) {
                                            director = new Director(directorName);
                                            directorRepository.save(director);
                                        }
                                        directors.add(director);
                                    }

                                    movie.setDirectors(directors);

                                    //---------------------------------------------------------------------------
                                    // SET AND SAVE ACTORS

                                    String actorString = detailedData.optString("Actors", "N/A");
                                    String[] actorArray = actorString.split(",\\s*");

                                    Set<Actor> actors = new HashSet<>();

                                    for (String actorName : actorArray) {
                                        Actor actor = actorRepository.findActorByFullName(actorName);

                                        if (actor == null) {
                                            actor = new Actor(actorName);
                                            actorRepository.save(actor);
                                        }
                                        actors.add(actor);
                                    }

                                    movie.setActors(actors);

                                    //---------------------------------------------------------------------------
                                    // SET AND SAVE GENRES

                                    String genreString = detailedData.optString("Genre", "N/A");
                                    String[] genreArray = genreString.split(",\\s*");

                                    Set<Genre> genres = new HashSet<>();

                                    for (String genreName : genreArray) {
                                        Genre genre = genreRepository.findByGenreName(genreName);

                                        if (genre == null) {
                                            genre = new Genre(genreName);
                                            genreRepository.save(genre);
                                        }
                                        genres.add(genre);
                                    }

                                    movie.setGenres(genres);

                                    // Save the movie to the database
                                    movieRepository.save(movie);
                                    results.add(movie);
                                }

                                detailedConn.disconnect();
                            }
                        }
                    } else {
                        System.out.println("HTTP error code: " + conn.getResponseCode());
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return results; // Return the list of Movie objects
        }



    }




