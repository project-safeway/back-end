package com.safeway.tech.controllers;

import com.safeway.tech.dto.AuthResponse;
import com.safeway.tech.dto.LoginRequest;
import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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
        AuthResponse autenticado = authService.autenticar(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(autenticado);
    }
}