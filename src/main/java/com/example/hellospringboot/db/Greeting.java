package com.example.hellospringboot.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JPA Data object. This is what gets stored when a request comes in.
 * The class name must match the table name (unless specified with @Entity(name="..."))
 * See <a href="https://spring.io/guides/gs/accessing-data-jpa/">example</a>.
 */
@Entity
public class Greeting {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private LocalDateTime time;

    public Greeting(String name, LocalDateTime time) {
        this.name = name;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("Greeting[id=%d, name=%s, time=%s", id, name, time.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
