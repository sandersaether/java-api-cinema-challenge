package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "num_seats")
    private int numSeats;
    @Column(name = "created_at", nullable = false)
    private String createdAt;
    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("tickets")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonIgnoreProperties("tickets")
    private Screening screening;


    public Ticket(int numSeats, Customer customer, Screening screening) {
        this.numSeats = numSeats;
        this.customer = customer;
        this.screening = screening;
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
    }

    public Ticket(int id) {
        this.id = id;
    }
}
