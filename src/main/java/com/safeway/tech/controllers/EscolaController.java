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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escolas")
@RequiredArgsConstructor
public class EscolaController {
    private final EscolaService escolaService;

    @PostMapping
    public ResponseEntity<EscolaResponse> cadastrarEscola(
            @Valid @RequestBody EscolaRequest request,
            @RequestParam Long usuarioId) {

        EscolaResponse response = escolaService.cadastrarEscola(request, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EscolaComAlunosResponse>> listarEscolasComAlunos(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long usuarioId = Long.parseLong(userDetails.getUsername());
        List<EscolaComAlunosResponse> response = escolaService.listarEscolasComAlunos(usuarioId);

        return ResponseEntity.ok(response);
    }
}