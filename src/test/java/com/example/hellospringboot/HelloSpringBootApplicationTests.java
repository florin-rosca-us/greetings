package com.example.hellospringboot;

import io.opentracing.Tracer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HelloSpringBootApplicationTests {

	@Autowired
	private HelloSpringBootController controller;

	 @Autowired
	 private Tracer tracer;


	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(tracer).isNotNull();
	}


}
