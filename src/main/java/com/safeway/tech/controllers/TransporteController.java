package com.safeway.tech.controllers;

import com.safeway.tech.dto.TransporteRequest;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.services.TransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transporte")
public class TransporteController {
    @Autowired
    private TransporteService transporteService;

    @PostMapping
    public ResponseEntity<Transporte> cadastrarTransporte(@RequestBody TransporteRequest request){
        Transporte transporte =  transporteService.cadastrarTransporte(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(transporte);
    }

    @GetMapping("/{id}")
    public Transporte retornarUm(@PathVariable Long id){
        return transporteService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id){
        transporteService.excluirTransporte(id);
    }

    @PutMapping("/{id}")
    public Transporte alterarTransporte(@RequestBody TransporteRequest request,@PathVariable Long id){
        return transporteService.alterarTransporte(request, id);
    }
}
