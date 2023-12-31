package com.example.hellospringboot;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * From <a href="https://spring.io/guides/gs/testing-web/">spring.io</a>
 * This is an integration test because accessing the endpoint sends UDP packets to Jaeger.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloSpringBootControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/greeting", String.class)).contains("Hello, World");
    }
}
