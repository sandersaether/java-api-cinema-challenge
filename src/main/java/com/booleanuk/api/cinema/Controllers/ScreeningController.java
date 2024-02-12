package com.booleanuk.api.cinema.Controllers;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Screening> getAll() {
        return this.screeningRepository.findAll();
    }

    @GetMapping("{id}")
    public Screening getById(@PathVariable("id") Integer id) {
        return this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Screening> create(@RequestBody Screening screening) {
        try {
            return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> deleteOneScreening(@PathVariable int id){
        Screening screeningToDelete = this.screeningRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        screeningToDelete.setTickets(new ArrayList<Ticket>());
        this.screeningRepository.delete(screeningToDelete);
        return new ResponseEntity<>(screeningToDelete, HttpStatus.ACCEPTED);
    }
}
