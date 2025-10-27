package com.safeway.tech.controllers;

import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.services.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Long> cadastrarAlunoCompleto(
            @RequestBody CadastroAlunoCompletoRequest request
    ) {
        Long idAluno = alunoService.cadastrarAlunoCompleto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(idAluno);
    }
}