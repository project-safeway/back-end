package com.safeway.tech.controllers;

import com.safeway.tech.models.Responsavel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.services.TransporteService;

import java.util.List;

@RestController
@RequestMapping("/Transporte")
public class TransporteController {
    @Autowired
    private TransporteService transporteService;

    @PostMapping
    public Transporte salvarTransporte(@RequestBody Transporte transporte){
        return transporteService.salvarTransporte(transporte);
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
    public void excluir(@PathVariable int idTransporte){
        transporteService.excluirTransporte(idTransporte);
    }

    @PutMapping("/{idTransporte}")
    public Transporte alterarTransporte(@RequestBody Transporte novoTransporte1,@PathVariable int idTransporte){
        return transporteService.alterarTransporte(novoTransporte1,idTransporte);
    }
}
