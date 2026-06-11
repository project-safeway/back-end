package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.evento.EventoRequest;
import com.safeway.tech.domain.enums.EventoTypeEnum;
import com.safeway.tech.domain.enums.PriorityEnum;
import com.safeway.tech.domain.models.Evento;
import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.infra.exception.EventoNotFoundException;
import com.safeway.tech.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioService usuarioService;
    private final CurrentUserService currentUserService;

    @Cacheable(cacheNames = "eventos", key = "#id + ':' + @currentUserService.getCurrentUserId()")
    public Evento buscarPorId(UUID id) {
        UUID userId = currentUserService.getCurrentUserId();
        return eventoRepository.findByIdAndIdUsuario(id, userId)
                .orElseThrow(() -> new EventoNotFoundException("Evento não encontrado"));
    }

    public List<Evento> listarFiltrado(LocalDate start, LocalDate end, String type, String priority) {
        UUID userId = currentUserService.getCurrentUserId();
        return eventoRepository.findFiltrado(
                userId,
                start,
                end,
                parseEventoType(type),
                parsePriority(priority)
        );
    }

    @CachePut(cacheNames = "eventos", key = "#result.id + ':' + @currentUserService.getCurrentUserId()")
    public Evento criarEvento(EventoRequest request) {
        Evento evento = new Evento();
        aplicarDados(evento, request);

        UUID userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.buscarPorId(userId);
        evento.setUsuario(usuario);

        return eventoRepository.save(evento);
    }

    @CachePut(cacheNames = "eventos", key = "#id + ':' + @currentUserService.getCurrentUserId()")
    public Evento atualizarEvento(UUID id, EventoRequest request) {
        UUID userId = currentUserService.getCurrentUserId();

        Evento evento = eventoRepository.findByIdAndIdUsuario(id, userId)
                .orElseThrow(() -> new EventoNotFoundException("Evento não encontrado"));

        aplicarDados(evento, request);
        return eventoRepository.save(evento);
    }

    @CacheEvict(cacheNames = "eventos", key = "#id + ':' + @currentUserService.getCurrentUserId()")
    public void excluir(UUID id) {
        UUID userId = currentUserService.getCurrentUserId();

        Evento evento = eventoRepository.findByIdAndIdUsuario(id, userId)
                .orElseThrow(() -> new EventoNotFoundException("Evento não encontrado"));

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

    private EventoTypeEnum parseEventoType(String type) {
        if (type == null || type.isBlank()) {
            return null;
        }
        return EventoTypeEnum.valueOf(type.trim().toUpperCase(Locale.ROOT));
    }

    private PriorityEnum parsePriority(String priority) {
        if (priority == null || priority.isBlank()) {
            return null;
        }
        return PriorityEnum.valueOf(priority.trim().toUpperCase(Locale.ROOT));
    }
}
