package com.safeway.tech.controllers;

import com.safeway.tech.models.Responsavel;
import com.safeway.tech.services.ResponsavelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsavel")
public class ResponsavelController {
    @Autowired
    private ResponsavelService responsavelService;

    @PostMapping
    public Responsavel salvarResponsavel(@RequestBody Responsavel responsavel){
        return responsavelService.salvarResponsavel(responsavel);
    }

    @GetMapping
    public List<Responsavel> listarResponsaveis() {
        return responsavelService.listarResponsaveis();
    }

    @GetMapping("/{idResponsavel}")
    public Responsavel retornarUm(@PathVariable Long idResponsavel){
        return responsavelService.retornarUm(idResponsavel);
    }

    @DeleteMapping("/{idResponsavel}")
    public void excluir(@PathVariable Long idResponsavel) {
        responsavelService.excluir(idResponsavel);
    }

    @PutMapping("/{idResponsavel}")
    public Responsavel alterarResponsavel(@RequestBody Responsavel novoResponsavel,@PathVariable Long idResponsavel){
        return responsavelService.alterarResponsavel(novoResponsavel, idResponsavel);
    }
}
