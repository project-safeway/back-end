package com.safeway.tech.controllers;

import com.safeway.tech.dto.AlunoResponse;
import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.dto.AlunoUpdateRequest;
import com.safeway.tech.services.AlunoService;
import com.safeway.tech.services.EnderecoService;
import com.safeway.tech.services.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Long> cadastrarAlunoCompleto(
            @RequestBody @Valid CadastroAlunoCompletoRequest request
    ) {
        Long idAluno = alunoService.cadastrarAlunoCompleto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(idAluno);
    }

    @GetMapping("/{alunoId}/enderecos")
    public ResponseEntity<List<EnderecoResponse>> listarEnderecosDoAluno(
            @PathVariable Long alunoId,
            @RequestParam(required = false) Long usuarioId
    ) {
        if (usuarioId == null) {
            usuarioId = currentUserService.getCurrentUserId();
        }
        List<EnderecoResponse> enderecos = enderecoService.listarEnderecosDisponiveis(alunoId, usuarioId);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity<AlunoResponse> listarDadosAluno(@PathVariable Long alunoId) {
        AlunoResponse alunoResponse = alunoService.obterDadosAluno(alunoId);
        return ResponseEntity.ok(alunoResponse);
    }

    @PutMapping("/{alunoId}")
    public ResponseEntity<AlunoResponse> atualizarAluno(
            @PathVariable Long alunoId,
            @RequestBody @Valid AlunoUpdateRequest request
    ) {
        AlunoResponse atualizado = alunoService.atualizarAluno(alunoId, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{alunoId}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long alunoId) {
        alunoService.deletarAluno(alunoId);
        return ResponseEntity.noContent().build();
    }
}