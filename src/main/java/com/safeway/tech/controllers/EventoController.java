package com.safeway.tech.controllers;

import com.safeway.tech.models.Evento;
import com.safeway.tech.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String priority) {
        return eventoService.listarFiltrado(startDate, endDate, type, priority);
    }

    @GetMapping("/{id}")
    public Evento obter(@PathVariable Long id) {
        return eventoService.retornarUmOwned(id);
    }

    @PostMapping
    public Evento criar(@RequestBody Evento evento) {
        return eventoService.criarEvento(evento);
    }

    @PutMapping("/{id}")
    public Evento atualizar(@PathVariable Long id, @RequestBody Evento evento) {
        return eventoService.atualizarEvento(id, evento);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        eventoService.excluir(id);
    }
}
