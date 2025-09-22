package com.safeway.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SafewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafewayApplication.class, args);
	}

}
