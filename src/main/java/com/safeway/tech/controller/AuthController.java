package com.safeway.tech.controller;

import com.safeway.tech.dto.AuthResponse;
import com.safeway.tech.dto.ConfirmSignUpRequest;
import com.safeway.tech.dto.LoginRequest;
import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        // cadastra sempre como Common
        authService.register(
                request.getNome(),
                request.getEmail(),
                request.getSenha()
        );
        return "Usuário registrado com sucesso!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getSenha());
    }

    @PostMapping("/confirm")
    public String confirmUser(@RequestBody ConfirmSignUpRequest request) {
        authService.confirmSignUpByEmail(request.getEmail(), request.getConfirmationCode());
        return "Usuário confirmado com sucesso!";
    }
}
