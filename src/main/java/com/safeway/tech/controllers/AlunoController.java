package com.safeway.tech.controllers;

import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.services.AlunoService;
import com.safeway.tech.services.EnderecoService;
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

    @PostMapping
    public ResponseEntity<Long> cadastrarAlunoCompleto(
            @RequestBody @Valid CadastroAlunoCompletoRequest request
    ) {
        Long idAluno = alunoService.cadastrarAlunoCompleto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(idAluno);
    }

    @GetMapping("/{alunoId}/enderecos")
    public ResponseEntity<List<EnderecoResponse>> listarEnderecosDoAluno(
            @PathVariable Long alunoId
    ) {
        List<EnderecoResponse> enderecos = enderecoService.listarPorAluno(alunoId);
        return ResponseEntity.ok(enderecos);
    }
}