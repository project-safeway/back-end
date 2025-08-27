package com.safeway.tech;

import com.safeway.tech.config.CognitoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CognitoConfig.class)
public class SafewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafewayApplication.class, args);
	}

}
