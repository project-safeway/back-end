package com.safeway.tech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CognitoConfig cognitoConfig;

    public SecurityConfig(CognitoConfig cognitoConfig) {
        this.cognitoConfig = cognitoConfig;
    }

    @Bean
    SecurityFilterChain publicEndpoints(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/auth/register", "/auth/login", "auth/confirm") // rotas públicas
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    SecurityFilterChain protectedEndpoints(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("Admin")
                        .requestMatchers("/user/**").hasRole("Common")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(cognitoConfig.getJwk()).build();
    }
}