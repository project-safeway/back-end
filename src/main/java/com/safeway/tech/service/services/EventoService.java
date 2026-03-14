package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.evento.EventoRequest;
import com.safeway.tech.domain.models.Evento;
import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.infra.exception.EventoNotFoundException;
import com.safeway.tech.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioService usuarioService;
    private final CurrentUserService currentUserService;

    public Evento buscarPorId(UUID idEvento) {
        UUID userId = currentUserService.getCurrentUserId();
        return eventoRepository.findByIdAndIdUsuario(idEvento, userId)
                .orElseThrow(() -> new EventoNotFoundException("Evento não encontrado"));
    }

    public List<Evento> listarFiltrado(LocalDate start, LocalDate end, String type, String priority) {
        UUID userId = currentUserService.getCurrentUserId();
        return eventoRepository.findFiltrado(userId, start, end, type, priority);
    }

    public Evento criarEvento(EventoRequest request) {
        Evento evento = new Evento();
        aplicarDados(evento, request);

        UUID userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.buscarPorId(userId);
        evento.setUsuario(usuario);

        return eventoRepository.save(evento);
    }

    public Evento atualizarEvento(UUID idEvento, EventoRequest request) {
        Evento evento = buscarPorId(idEvento);
        aplicarDados(evento, request);
        return eventoRepository.save(evento);
    }

    public void excluir(UUID id) {
        Evento evento = buscarPorId(id);

        UUID userId = currentUserService.getCurrentUserId();
        if (!evento.getUsuario().getId().equals(userId)) {
            throw new IllegalArgumentException("Ação não permitida");
        }

        eventoRepository.delete(evento);
    }

    private void aplicarDados(Evento evento, EventoRequest request) {
        evento.setTitle(request.title());
        evento.setDescription(request.description());
        evento.setDate(request.date());
        evento.setType(request.type());
        evento.setPriority(request.priority());
    }
}
