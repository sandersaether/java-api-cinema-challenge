package com.booleanuk.api.cinema.Controllers;

import com.booleanuk.api.cinema.Response;
import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.models.Ticket;
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
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return Response.generateResponse(HttpStatus.OK, this.customerRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Integer id) {
        try{
            Customer customer = this.customerRepository
                    .findById(id)
                    .orElseThrow(Exception::new);
            return Response.generateResponse(HttpStatus.OK, customer);
        }
        catch (Exception e){
            return Response.generateError(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOneCustomer(@PathVariable int id, @RequestBody Customer customer) {
        try {
            Customer customerToUpdate = this.customerRepository
                    .findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        if (customer.getName() == null || customer.getPhone() == null || customer.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        customerToUpdate.setName(customer.getName());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setUpdatedAt(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString());

        return new ResponseEntity<>(this.customerRepository.save(customerToUpdate), HttpStatus.CREATED);}
        catch (Exception e){
            return Response.generateError(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteOneCustomer(@PathVariable int id){
        Customer customerToDelete = this.customerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        customerToDelete.setTickets(new ArrayList<Ticket>());
        this.customerRepository.delete(customerToDelete);
        return new ResponseEntity<>(customerToDelete, HttpStatus.OK);
    }
}
