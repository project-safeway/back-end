package com.safeway.tech.controllers;

import com.safeway.tech.models.Evento;
import com.safeway.tech.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> listar(@AuthenticationPrincipal Jwt jwt,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                               @RequestParam(required = false) String type,
                               @RequestParam(required = false) String priority) {
        Long clientId = Long.valueOf(jwt.getSubject());
        return eventoService.listarFiltrado(clientId, startDate, endDate, type, priority);
    }

    @GetMapping("/{id}")
    public Evento obter(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        Long clientId = Long.valueOf(jwt.getSubject());
        var evt = eventoService.retornarUm(id);
        if (evt == null) throw new RuntimeException("Evento não encontrado");
        if (!evt.getClientId().equals(clientId)) throw new RuntimeException("Você não tem permissão para acessar este evento");
        return evt;
    }

    @PostMapping
    public Evento criar(@AuthenticationPrincipal Jwt jwt, @RequestBody Evento evento) {
        Long clientId = Long.valueOf(jwt.getSubject());
        if (evento.getPriority() == null || evento.getPriority().isBlank()) {
            evento.setPriority("media");
        }
        evento.setClientId(clientId);
        return eventoService.salvarEvento(evento);
    }

    @PutMapping("/{id}")
    public Evento atualizar(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody Evento evento) {
        Long clientId = Long.valueOf(jwt.getSubject());
        var atual = eventoService.retornarUm(id);
        if (atual == null) throw new RuntimeException("Evento não encontrado");
        if (!atual.getClientId().equals(clientId)) throw new RuntimeException("Você não tem permissão para editar este evento");

        atual.setTitle(evento.getTitle());
        atual.setDescription(evento.getDescription());
        atual.setDate(evento.getDate());
        atual.setType(evento.getType());
        atual.setPriority(evento.getPriority() == null || evento.getPriority().isBlank() ? "media" : evento.getPriority());
        return eventoService.salvarEvento(atual);
    }

    @DeleteMapping("/{id}")
    public void excluir(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        Long clientId = Long.valueOf(jwt.getSubject());
        var evt = eventoService.retornarUm(id);
        if (evt == null) throw new RuntimeException("Evento não encontrado");
        if (!evt.getClientId().equals(clientId)) throw new RuntimeException("Você não tem permissão para deletar este evento");
        eventoService.excluir(id);
    }
}
