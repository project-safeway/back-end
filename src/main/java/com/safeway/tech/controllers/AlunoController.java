package com.safeway.tech.controllers;

import com.safeway.tech.dto.AlunoResponse;
import com.safeway.tech.dto.AlunoUpdateRequest;
import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.services.AlunoService;
import com.safeway.tech.services.CurrentUserService;
import com.safeway.tech.services.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private CurrentUserService currentUserService;

    @PostMapping
    public ResponseEntity<UUID> cadastrarAlunoCompleto(
            @RequestBody @Valid CadastroAlunoCompletoRequest request
    ) {
        UUID idAluno = alunoService.cadastrarAlunoCompleto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(idAluno);
    }

    @GetMapping("/{alunoId}/enderecos")
    public ResponseEntity<List<EnderecoResponse>> listarEnderecosDoAluno(
            @PathVariable UUID alunoId,
            @RequestParam(required = false) UUID usuarioId
    ) {
        if (usuarioId == null) {
            usuarioId = currentUserService.getCurrentUserId();
        }
        List<EnderecoResponse> enderecos = enderecoService.listarEnderecosDisponiveis(alunoId, usuarioId);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity<AlunoResponse> listarDadosAluno(@PathVariable UUID alunoId) {
        AlunoResponse alunoResponse = alunoService.obterDadosAluno(alunoId);
        return ResponseEntity.ok(alunoResponse);
    }

    @PutMapping("/{alunoId}")
    public ResponseEntity<AlunoResponse> atualizarAluno(
            @PathVariable UUID alunoId,
            @RequestBody @Valid AlunoUpdateRequest request
    ) {
        AlunoResponse atualizado = alunoService.atualizarAluno(alunoId, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{alunoId}")
    public ResponseEntity<Void> deletarAluno(@PathVariable UUID alunoId) {
        alunoService.deletarAluno(alunoId);
        return ResponseEntity.noContent().build();
    }
}