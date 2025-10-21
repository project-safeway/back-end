package com.safeway.tech.controllers;

import com.safeway.tech.dto.FuncionarioRequest;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    /*

        TODO - Implementar as validações necessárias no endpoint de atualização de funcionário.
        (Vale mais a pena criarmos um novo dto para usar as anotações?)

     */

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody @Valid FuncionarioRequest request){
        Funcionario funcionario = funcionarioService.salvarFuncionario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionario);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios(){
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> retornarUm(@PathVariable Long id){
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Long id){
        funcionarioService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> alterarFuncionario(@RequestBody FuncionarioRequest request,@PathVariable Long id){
        Funcionario funcionario = funcionarioService.alterarFuncionario(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }
}
