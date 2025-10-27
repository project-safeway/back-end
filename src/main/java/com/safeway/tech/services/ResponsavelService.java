package com.safeway.tech.services;

import com.safeway.tech.models.Responsavel;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsavelService {
    private ResponsavelRepository repository;

     public Responsavel getById(Long id){
         return repository.findById(id).orElseThrow(RuntimeException::new);
     }

     public Responsavel salvarResponsavel(Responsavel responsavel){
         Responsavel responsavel1 = repository.save(responsavel);
         System.out.println("Responsavel cadastrado!");
         return responsavel1;
     }

    public List<Responsavel> listarResponsaveis() {
        return repository.findAll();
    }

    public Responsavel retornarUm(Long idResponsavel){
        return repository.findById(idResponsavel).orElseThrow(RuntimeException::new);
    }

     public void excluir(Long id){
          repository.delete(getById(id));
     }

     public Responsavel alterarResponsavel(Responsavel responsavel,Long idResponsavel){
         Responsavel responsavel1 = getById(responsavel.getIdResponsavel());
         responsavel1.setNome(responsavel.getNome());
         responsavel1.setEndereco(responsavel.getEndereco());
         responsavel1.setAlunos(responsavel.getAlunos());
         System.out.println("Responsavel Atualizado!");
         return repository.save(responsavel1);
     }
}
