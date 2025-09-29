package com.safeway.tech.services;

import com.safeway.tech.models.Funcionario;
import com.safeway.tech.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    private FuncionarioRepository repository;

    public Funcionario getById(long idFuncionario){
        return repository.findById(idFuncionario).orElseThrow(() -> new RuntimeException());
    }

    public Funcionario salvarFuncionario(Funcionario funcionario){
        Funcionario funcionario1 = repository.save(funcionario);
        System.out.println("Funcionario cadastrado!");
        return funcionario1;
    }

    public List<Funcionario> listarFuncionarios(){
        return repository.findAll();
    }

    public void excluir(long idFuncionario){
        repository.delete(getById(idFuncionario));
    }

    public Funcionario alterarFuncionario(Funcionario funcionario, long idFuncionario){
        Funcionario funcionario1 = getById(funcionario.getIdFuncionario());
        funcionario1.setTransporte(funcionario.getTransporte());
        funcionario1.setEndereco(funcionario.getEndereco());
        funcionario1.setNome(funcionario.getNome());
        funcionario1.setCpf(funcionario.getCpf());
        funcionario1.setPagamentos(funcionario.getPagamentos());
        System.out.println("Funcionario Atualizado!");
        return repository.save(funcionario1);
    }
}
