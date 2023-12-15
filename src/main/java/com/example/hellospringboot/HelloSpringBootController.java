package com.example.hellospringboot;

import com.example.hellospringboot.db.Greeting;
import com.example.hellospringboot.db.GreetingResponse;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * From <a href="https://spring.io/guides/gs/rest-service/">spring.io</a>
 */
@RestController
public class HelloSpringBootController {

    private final Logger log = LoggerFactory.getLogger(HelloSpringBootController.class);

    private final GreetingService service;
    private final Tracer tracer;

    public HelloSpringBootController(GreetingService service, Tracer tracer) {
        this.service = service;
        this.tracer = tracer;
    }

    private static final String template = "Hello, %s!";


    @GetMapping("/greeting")
    public GreetingResponse greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Span span = tracer.buildSpan("greeting").start(); // ???
        log.debug("Inside greeting...");
        span.log("This is a span log message");

        Greeting greeting = service.create(name);

        span.finish();
        return new GreetingResponse(greeting.getId(), String.format(template, greeting.getName()));
    }
}
