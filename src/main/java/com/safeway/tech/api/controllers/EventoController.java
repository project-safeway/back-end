package com.safeway.tech.api.controllers;

import com.safeway.tech.api.dto.evento.EventoRequest;
import com.safeway.tech.api.dto.evento.EventoResponse;
import com.safeway.tech.domain.models.Evento;
import com.safeway.tech.service.mappers.EventoMapper;
import com.safeway.tech.service.services.EventoService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoResponse>> listarFiltrado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String priority) {
        List<Evento> eventos = eventoService.listarFiltrado(start, end, type, priority);
        List<EventoResponse> responses = eventos.stream().map(EventoMapper::toResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> obterPorId(@PathVariable UUID id) {
        Evento evento = eventoService.buscarPorId(id);
        EventoResponse response = EventoMapper.toResponse(evento);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<EventoResponse> criarEvento(@RequestBody EventoRequest request) {
        Evento evento = eventoService.criarEvento(request);
        EventoResponse response = EventoMapper.toResponse(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> atualizar(@PathVariable UUID id, @RequestBody EventoRequest request) {
        Evento evento = eventoService.atualizarEvento(id, request);
        EventoResponse response = EventoMapper.toResponse(evento);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        eventoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
