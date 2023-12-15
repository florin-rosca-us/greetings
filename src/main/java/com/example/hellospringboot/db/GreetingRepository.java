package com.example.hellospringboot.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * JPA repository.
 */
public interface GreetingRepository extends CrudRepository<Greeting, Integer> {
    Greeting findById(int id);
    List<Greeting> findByName(String name);
}
