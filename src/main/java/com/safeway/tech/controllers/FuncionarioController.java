package com.safeway.tech.controllers;

import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.services.FuncionarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    private FuncionarioService funcionarioService;

    @PostMapping
    public Funcionario salvarFuncionario(@RequestBody Funcionario funcionario){
        return funcionarioService.salvarFuncionario(funcionario);
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
