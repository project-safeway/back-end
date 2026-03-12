package com.safeway.tech.api.controllers;

import com.safeway.tech.api.dto.auth.AuthResponse;
import com.safeway.tech.api.dto.auth.LoginRequest;
import com.safeway.tech.api.dto.auth.RegisterRequest;
import com.safeway.tech.service.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse autenticado = authService.autenticar(request.email(), request.senha());
        return ResponseEntity.ok(autenticado);
    }
}