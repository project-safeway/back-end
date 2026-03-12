package com.safeway.tech.api.controllers;

import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.api.dto.escola.EscolaComAlunosResponse;
import com.safeway.tech.api.dto.escola.EscolaRequest;
import com.safeway.tech.api.dto.escola.EscolaResponse;
import com.safeway.tech.service.services.EscolaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<EscolaResponse> buscarEscolaPorId(@PathVariable UUID id) {
        EscolaResponse response = escolaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/endereco")
    public ResponseEntity<EnderecoResponse> buscarEnderecoEscola(@PathVariable UUID id) {
        EnderecoResponse response = escolaService.buscarEnderecoDaEscola(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscolaResponse> atualizarEscola(
            @PathVariable UUID id,
            @Valid @RequestBody EscolaRequest request) {
        EscolaResponse response = escolaService.atualizarEscola(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEscola(@PathVariable UUID id) {
        escolaService.deletarEscola(id);
        return ResponseEntity.noContent().build();
    }
}
