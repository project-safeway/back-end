package com.safeway.tech.api.controllers;

import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.api.dto.escola.EscolaRequest;
import com.safeway.tech.api.dto.escola.EscolaResponse;
import com.safeway.tech.domain.models.Endereco;
import com.safeway.tech.domain.models.Escola;
import com.safeway.tech.service.mappers.EnderecoMapper;
import com.safeway.tech.service.mappers.EscolaMapper;
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

        Escola escola = escolaService.cadastrarEscola(request);
        EscolaResponse response = EscolaMapper.toResponse(escola);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EscolaResponse>> listarEscolasComAlunos() {
        List<Escola> escolas = escolaService.listarEscolasComAlunos();
        List<EscolaResponse> response = escolas.stream().map(EscolaMapper::toResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscolaResponse> buscarEscolaPorId(@PathVariable UUID id) {
        Escola escola = escolaService.buscarPorId(id);
        EscolaResponse response = EscolaMapper.toResponse(escola);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/endereco")
    public ResponseEntity<EnderecoResponse> buscarEnderecoEscola(@PathVariable UUID id) {
        Endereco endereco = escolaService.buscarEnderecoDaEscola(id);
        EnderecoResponse response = EnderecoMapper.toResponse(endereco);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscolaResponse> atualizarEscola(
            @PathVariable UUID id,
            @Valid @RequestBody EscolaRequest request) {
        Escola escola = escolaService.atualizarEscola(id, request);
        EscolaResponse response = EscolaMapper.toResponse(escola);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEscola(@PathVariable UUID id) {
        escolaService.desativar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
