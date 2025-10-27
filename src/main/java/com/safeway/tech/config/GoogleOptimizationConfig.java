package com.safeway.tech.config;

import com.google.api.client.util.Value;
import com.safeway.tech.client.GoogleOptimizationClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GoogleOptimizationConfig {
    @Value("${google.projectId}")
    private String projectId;

    @Bean
    public GoogleOptimizationClient googleOptimizationClient(WebClient.Builder builder) {
        return new GoogleOptimizationClient(builder, projectId);
    }
}
