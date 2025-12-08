package com.safeway.tech.controllers;

import com.safeway.tech.dto.TransporteRequest;
import com.safeway.tech.dto.TransporteResponse;
import com.safeway.tech.dto.AlunoTransporteResponse;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.services.TransporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transporte")
public class TransporteController {
    @Autowired
    private TransporteService transporteService;

    private Transporte mapToEntity(TransporteRequest req){
        Transporte t = new Transporte();
        t.setPlaca(req.placa());
        t.setModelo(req.modelo());
        t.setCapacidade(req.capacidade());
        return t;
    }

    private TransporteResponse mapToResponse(Transporte t){
        return TransporteResponse.fromEntity(t);
    }

    @PostMapping
    public TransporteResponse salvarTransporte(@RequestBody @Valid TransporteRequest request){
        Transporte salvo = transporteService.salvarTransporte(mapToEntity(request));
        return mapToResponse(salvo);
    }

    @GetMapping
    public List<TransporteResponse> listarTransportes(){
        return transporteService.listarTransportes()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{idTransporte}")
    public TransporteResponse retornarUm(@PathVariable long idTransporte){
        return mapToResponse(transporteService.getById(idTransporte));
    }

    @GetMapping("/{idTransporte}/alunos")
    public List<AlunoTransporteResponse> listarAlunosDoTransporte(@PathVariable long idTransporte) {
        return transporteService.listarAlunos(idTransporte)
                .stream()
                .map(AlunoTransporteResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{idTransporte}")
    public void excluir(@PathVariable long idTransporte){
        transporteService.excluirTransporte(idTransporte);
    }

    @PutMapping("/{idTransporte}")
    public TransporteResponse alterarTransporte(@RequestBody @Valid TransporteRequest novoTransporte1,@PathVariable long idTransporte){
        Transporte atualizado = transporteService.alterarTransporte(mapToEntity(novoTransporte1),idTransporte);
        return mapToResponse(atualizado);
    }
}
