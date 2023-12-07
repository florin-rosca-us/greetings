package com.example.hellospringboot;

import java.util.concurrent.atomic.AtomicLong;

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

    private final Tracer tracer;

    public HelloSpringBootController(Tracer tracer) {
        this.tracer = tracer;
    }

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    protected final Logger logger = LoggerFactory.getLogger(HelloSpringBootController.class);

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Span span = tracer.buildSpan("greeting").start(); // ???
        logger.debug("Inside greeting...");
        span.log("This is a span log message");
        span.finish();
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
