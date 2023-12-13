package com.example.hellospringboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelloSpringBootApplicationTest {

    @Autowired
    private HelloSpringBootApplication application;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
    }
}
