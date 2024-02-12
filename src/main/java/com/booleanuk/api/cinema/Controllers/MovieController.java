package com.booleanuk.api.cinema.Controllers;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.models.Screening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @GetMapping("{id}")
    public Movie getById(@PathVariable("id") Integer id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        try {
            return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateOneMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString());

        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteOneMovie(@PathVariable int id){
        Movie movieToDelete = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<Screening>());
        return new ResponseEntity<>(movieToDelete, HttpStatus.ACCEPTED);
    }
}
