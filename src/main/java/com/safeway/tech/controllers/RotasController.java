package com.safeway.tech.controllers;

import com.safeway.tech.dto.rotas.RotasRequest;
import com.safeway.tech.dto.rotas.RotasResponse;
import com.safeway.tech.services.RotasCompostasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rotas")
@CrossOrigin(origins = "http://localhost:5173")
public class RotasController {

    @Autowired
    private RotasCompostasService rotasCompostasService;

    @PostMapping("/otimizar")
    public RotasResponse otimizarRota(@RequestBody RotasRequest request) {
        return rotasCompostasService.otimizarMelhorRota(request);
    }


}
