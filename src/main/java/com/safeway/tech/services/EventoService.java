package com.safeway.tech.services;

import com.safeway.tech.models.Evento;
import com.safeway.tech.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    public Evento salvarEvento(Evento evento) {
        return repository.save(evento);
    }

    public List<Evento> listarEventos() {
        return repository.findAll();
    }

    public Evento retornarUm(Long idEvento) {
        return repository.findById(idEvento).orElse(null);
    }

    public Optional<Evento> buscarPorId(Long idEvento) {
        return repository.findById(idEvento);
    }

    public void excluir(Long idEvento) {
        repository.deleteById(idEvento);
    }

    public Evento alterarEvento(Long idEvento, Evento novo) {
        Evento atual = retornarUm(idEvento);
        if (atual == null) return null;
        atual.setTitle(novo.getTitle());
        atual.setDescription(novo.getDescription());
        atual.setDate(novo.getDate());
        atual.setType(novo.getType());
        atual.setPriority(novo.getPriority());
        // mantém o clientId original
        return repository.save(atual);
    }

    public List<Evento> listarFiltrado(Long clientId,
                                       LocalDate start,
                                       LocalDate end,
                                       String type,
                                       String priority) {
        return repository.findFiltrado(clientId, start, end, type, priority);
    }
}
