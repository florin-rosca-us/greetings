package com.example.hellospringboot;

import com.example.hellospringboot.db.Greeting;
import com.example.hellospringboot.db.GreetingRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GreetingService {

    private final GreetingRepository repository;

    public GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }

    public Greeting create(String name) {
        Greeting greeting = new Greeting(name, LocalDateTime.now());
        repository.save(greeting);
        return greeting;
    }
}
