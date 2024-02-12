package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "created_at", nullable = false)
    private String createdAt;
    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private List<Ticket> tickets;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
    }


    public Customer() {
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
    }
    public Customer(int id) {
        this.id = id;
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
    }
}
