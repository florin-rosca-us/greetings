package com.example.hellospringboot.db;

/**
 * This is what HelloSpringBootController.greeting returns as JSON
 *
 * @param id
 * @param content
 */
public record GreetingResponse(long id, String content) { }
