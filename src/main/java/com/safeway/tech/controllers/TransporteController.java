package com.safeway.tech.controllers;

import com.safeway.tech.dto.TransporteRequest;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.services.TransporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public Transporte salvarTransporte(@RequestBody @Valid TransporteRequest request){
        return transporteService.salvarTransporte(mapToEntity(request));
    }

    @GetMapping
    public List<Transporte> listarTransportes(){
        return transporteService.listarTransportes();
    }

    @GetMapping("/{idTransporte}")
    public Transporte retornarUm(@PathVariable long idTransporte){
        return transporteService.getById(idTransporte);
    }

    @DeleteMapping("/{idTransporte}")
    public void excluir(@PathVariable long idTransporte){
        transporteService.excluirTransporte(idTransporte);
    }

    @PutMapping("/{idTransporte}")
    public Transporte alterarTransporte(@RequestBody @Valid TransporteRequest novoTransporte1,@PathVariable long idTransporte){
        return transporteService.alterarTransporte(mapToEntity(novoTransporte1),idTransporte);
    }
}
