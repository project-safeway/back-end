package com.safeway.tech.services;

import com.safeway.tech.models.Transporte;
import com.safeway.tech.repository.TransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransporteService {
    private TransporteRepository repository;

    public Transporte getById(long idTransporte){
        return  repository.findById(idTransporte).orElseThrow(() -> new RuntimeException());
    }

    public Transporte salvarTransporte(Transporte transporte){
        Transporte transporte1 = repository.save(transporte);
        System.out.println("Transporte cadastrado!");
        return transporte1;
    }

    public List<Transporte> listarTransportes(){
        return repository.findAll();
    }

    public void excluirTransporte(long idTransporte){
        repository.delete(getById(idTransporte));
    }

    public Transporte alterarTransporte(Transporte transporte, int idTransporte){
     Transporte transporte1 = getById(transporte.getIdTransporte());
     transporte1.setPlaca(transporte.getPlaca());
     transporte1.setModelo(transporte.getModelo());
     transporte1.setCapacidade(transporte.getCapacidade());
     transporte1.setFuncionarios(transporte.getFuncionarios());
     transporte1.setAlunosTransportes(transporte.getAlunosTransportes());
     transporte1.setDespesas(transporte.getDespesas());
     System.out.println("Transporte Atualizado!");
     return repository.save(transporte1);
    }
}
