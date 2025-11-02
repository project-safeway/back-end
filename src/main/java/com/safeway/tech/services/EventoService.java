package com.safeway.tech.services;

import com.safeway.tech.models.Evento;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.EventoRepository;
import com.safeway.tech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Evento criarEvento(Evento evento) {
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);
        if (evento.getPriority() == null || evento.getPriority().isBlank()) {
            evento.setPriority("media");
        }
        evento.setUsuario(usuario);
        return repository.save(evento);
    }

    public List<Evento> listarEventosDoUsuarioAtual() {
        Long userId = currentUserService.getCurrentUserId();
        return repository.findAllByUsuario_IdUsuario(userId);
    }

    public Evento retornarUmOwned(Long idEvento) {
        Long userId = currentUserService.getCurrentUserId();
        return repository.findByIdAndUsuario_IdUsuario(idEvento, userId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    public void excluir(Long idEvento) {
        // valida propriedade
        Evento evt = retornarUmOwned(idEvento);
        repository.deleteById(evt.getId());
    }

    public Evento atualizarEvento(Long idEvento, Evento novo) {
        Evento atual = retornarUmOwned(idEvento);
        atual.setTitle(novo.getTitle());
        atual.setDescription(novo.getDescription());
        atual.setDate(novo.getDate());
        atual.setType(novo.getType());
        atual.setPriority(novo.getPriority() == null || novo.getPriority().isBlank() ? "media" : novo.getPriority());
        return repository.save(atual);
    }

    public List<Evento> listarFiltrado(LocalDate start,
                                       LocalDate end,
                                       String type,
                                       String priority) {
        Long userId = currentUserService.getCurrentUserId();
        return repository.findFiltrado(userId, start, end, type, priority);
    }
}
