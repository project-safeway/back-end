package com.safeway.tech.services;

import com.safeway.tech.entity.Responsavel;
import com.safeway.tech.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;

@Service
public class ResponsavelService {
    private ResponsavelRepository repository;

     public Responsavel getById(Long id){
         return repository.findById(id).orElseThrow(() -> new RuntimeException());
     }

     public Responsavel criar(Responsavel responsavel){
         Responsavel responsavel1 = repository.save(responsavel);
         System.out.println("Responsavel cadastrado!");
         return responsavel1;
     }

     public void excluir(Long id){
          repository.delete(getById(id));
     }

     public Responsavel atualizar(Responsavel responsavel){
         Responsavel responsavel1 = getById(responsavel.getId());
         responsavel1.setNome(responsavel.getNome());
         responsavel1.setEndereco(responsavel.getEndereco());
         responsavel1.setEmail(responsavel.getEmail());
         responsavel1.setSenha(responsavel.getSenha());
         responsavel1.setTelefone(responsavel.getTelefone());

         return repository.save(responsavel1);
     }
}
