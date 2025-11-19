package com.safeway.tech.controllers;

import com.safeway.tech.dto.EscolaComAlunosResponse;
import com.safeway.tech.dto.EscolaRequest;
import com.safeway.tech.dto.EscolaResponse;
import com.safeway.tech.services.EscolaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escolas")
@RequiredArgsConstructor
public class EscolaController {
    private final EscolaService escolaService;

    @PostMapping
    public ResponseEntity<EscolaResponse> cadastrarEscola(
            @Valid @RequestBody EscolaRequest request) {

        EscolaResponse response = escolaService.cadastrarEscola(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EscolaComAlunosResponse>> listarEscolasComAlunos() {
        List<EscolaComAlunosResponse> response = escolaService.listarEscolasComAlunos();
        return ResponseEntity.ok(response);
    }
}