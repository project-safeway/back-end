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

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody @Valid FuncionarioRequest request){
        Funcionario funcionario = funcionarioService.salvarFuncionario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionario);
    }

    @GetMapping
    public List<Funcionario> listarFuncionarios(){
        return funcionarioService.listarFuncionarios();
    }

    @GetMapping("/{idFuncionario}")
    public Funcionario retornarUm(@PathVariable long idFuncionario){
        return funcionarioService.getById(idFuncionario);
    }

    @DeleteMapping("/{idFuncionario}")
    public void excluirFuncionario(@PathVariable long idFuncionario){
        funcionarioService.excluir(idFuncionario);
    }

    @PutMapping("/{idFuncionario}")
    public Funcionario alterarFuncionario(@RequestBody Funcionario novoFuncionario,@PathVariable long idFuncionario){
        return funcionarioService.alterarFuncionario(novoFuncionario,idFuncionario);
    }
}
