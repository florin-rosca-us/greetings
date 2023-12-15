package com.example.hellospringboot;

import com.example.hellospringboot.db.Greeting;
import io.opentracing.Tracer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@SpringBootTest
class HelloSpringBootApplicationTest {

	@Autowired
	private HelloSpringBootApplication application;
	@Autowired
	private HelloSpringBootController controller;

	 @Autowired
	 private Tracer tracer;


	@Test
	void contextLoads() {

		GreetingService service = mock(GreetingService.class);
		when(service.create(anyString())).thenReturn(new Greeting("name", LocalDateTime.now()));

		assertThat(application).isNotNull();
		assertThat(controller).isNotNull();
		assertThat(tracer).isNotNull();
	}

}
