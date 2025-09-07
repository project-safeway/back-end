package com.safeway.tech.controllers;

import com.safeway.tech.dto.AuthResponse;
import com.safeway.tech.dto.LoginRequest;
import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("Usuário registrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            boolean autenticado = authService.autenticar(request.getEmail(), request.getSenha());
            if (autenticado) {
                return ResponseEntity.ok("Login realizado com sucesso");
            }
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}