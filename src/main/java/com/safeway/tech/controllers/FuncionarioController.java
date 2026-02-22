package com.safeway.tech.controllers;

import com.safeway.tech.dto.FuncionarioRequest;
import com.safeway.tech.dto.FuncionarioResponse;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.services.FuncionarioService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    /*

        TODO - Implementar as validações necessárias no endpoint de atualização de funcionário.
        (Vale mais a pena criarmos um novo dto para usar as anotações?)

     */

    @Autowired
    private FuncionarioService funcionarioService;

    private FuncionarioResponse toResponse(Funcionario f) {
        return FuncionarioResponse.fromEntity(f);
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponse> salvarFuncionario(@RequestBody @Valid FuncionarioRequest request){
        Funcionario funcionario = funcionarioService.salvarFuncionario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(funcionario));
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listarFuncionarios(){
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        List<FuncionarioResponse> resp = funcionarios.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> retornarUm(@PathVariable UUID id){
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(toResponse(funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable UUID id){
        funcionarioService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> alterarFuncionario(@RequestBody @Valid FuncionarioRequest request,@PathVariable UUID id){
        Funcionario funcionario = funcionarioService.alterarFuncionario(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(toResponse(funcionario));
    }
}
