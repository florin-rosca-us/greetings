package com.example.hellospringboot;

/**
 * This is what HelloSpringBootController.greeting returns
 *
 * @param id
 * @param content
 */
public record Greeting(long id, String content) { }
