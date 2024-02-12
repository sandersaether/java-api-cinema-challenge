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
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "rating", nullable = false)
    private String rating;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "runtime_mins")
    private int runtimeMins = -1;
    @Column(name = "created_at", nullable = false)
    private String createdAt;
    @Column(name = "updated_at", nullable = false)
    private String updatedAt;
    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtimeMins, List<Screening> screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.screenings = screenings;
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
    }

    public Movie() {
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
    }
    public Movie(int id) {
        this.id = id;
        this.createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
        this.updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("CET")).format(DateTimeFormatter.ISO_DATE_TIME).toString();
    }
}
