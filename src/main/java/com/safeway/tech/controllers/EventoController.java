package com.safeway.tech.controllers;

import com.safeway.tech.dto.EventoRequest;
import com.safeway.tech.models.Evento;
import com.safeway.tech.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoRequest>> listarFiltrado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String priority) {
        List<EventoRequest> eventos = eventoService.listarFiltrado(start, end, type, priority);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public Evento obter(@PathVariable UUID id) {
        return eventoService.retornarUmOwned(id);
    }

    @PostMapping
    public ResponseEntity<EventoRequest> criarEvento(@RequestBody Evento evento) {
        EventoRequest eventoDTO = eventoService.criarEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoDTO);
    }

    @PutMapping("/{id}")
    public Evento atualizar(@PathVariable UUID id, @RequestBody Evento evento) {
        return eventoService.atualizarEvento(id, evento);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        eventoService.excluir(id);
    }
}
