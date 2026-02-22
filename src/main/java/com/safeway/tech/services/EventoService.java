package com.safeway.tech.services;

import com.safeway.tech.dto.EventoRequest;
import com.safeway.tech.models.Evento;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.EventoRepository;
import com.safeway.tech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EventoRequest criarEvento(Evento evento) {
        UUID userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);
        if (evento.getPriority() == null || evento.getPriority().isBlank()) {
            evento.setPriority("media");
        }
        evento.setUsuario(usuario);
        Evento salvo = repository.save(evento);
        return converterParaDTO(salvo);
    }

    public List<EventoRequest> listarEventosDoUsuarioAtual() {
        UUID userId = currentUserService.getCurrentUserId();
        return repository.findAllByUsuario_IdUsuario(userId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Evento retornarUmOwned(UUID idEvento) {
        UUID userId = currentUserService.getCurrentUserId();
        return repository.findByIdAndUsuario_IdUsuario(idEvento, userId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    public void excluir(UUID idEvento) {
        // valida propriedade
        Evento evt = retornarUmOwned(idEvento);
        repository.deleteById(evt.getId());
    }

    public Evento atualizarEvento(UUID idEvento, Evento novo) {
        Evento atual = retornarUmOwned(idEvento);
        atual.setTitle(novo.getTitle());
        atual.setDescription(novo.getDescription());
        atual.setDate(novo.getDate());
        atual.setType(novo.getType());
        atual.setPriority(novo.getPriority() == null || novo.getPriority().isBlank() ? "media" : novo.getPriority());
        return repository.save(atual);
    }

    public List<EventoRequest> listarFiltrado(LocalDate start, LocalDate end, String type, String priority) {
        UUID userId = currentUserService.getCurrentUserId();
        return repository.findFiltrado(userId, start, end, type, priority)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    private EventoRequest converterParaDTO(Evento evento) {
        return new EventoRequest(
                evento.getId(),
                evento.getTitle(),
                evento.getDescription(),
                evento.getDate(),
                evento.getType(),
                evento.getPriority(),
                evento.getUsuario().getId(),
                evento.getUsuario().getNome(),
                evento.getCreatedAt(),
                evento.getUpdatedAt()
        );
    }
}
