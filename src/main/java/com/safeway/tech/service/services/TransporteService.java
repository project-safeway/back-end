package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.transporte.TransporteRequest;
import com.safeway.tech.domain.models.Aluno;
import com.safeway.tech.domain.models.Transporte;
import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.infra.exception.TransporteNotFoundException;
import com.safeway.tech.repository.TransporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransporteService {

    private final TransporteRepository transporteRepository;
    private final UsuarioService usuarioService;
    private final CurrentUserService currentUserService;

    public Transporte buscarPorId(UUID idTransporte) {
        UUID userId = currentUserService.getCurrentUserId();

        return transporteRepository.findByIdAndUsuarioId(idTransporte, userId)
                .orElseThrow(() -> new TransporteNotFoundException("Transporte não encontrado"));
    }

    public List<Aluno> listarAlunos(UUID idTransporte) {
        Transporte transporte = buscarPorId(idTransporte);
        return transporte.getAlunosTransportes();
    }

    public List<Transporte> listarTransportes() {
        UUID userId = currentUserService.getCurrentUserId();
        return transporteRepository.findAllByIdUsuario(userId);
    }

    public Transporte salvarTransporte(TransporteRequest request) {
        Transporte transporte = new Transporte();

        aplicarDados(transporte, request);

        UUID userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.buscarPorId(userId);

        transporte.setUsuario(usuario);

        return transporteRepository.save(transporte);
    }

    public Transporte atualizarTransporte(UUID idTransporte, TransporteRequest request) {
        Transporte transporte = buscarPorId(idTransporte);

        aplicarDados(transporte, request);

        return transporteRepository.save(transporte);
    }

    public void excluirTransporte(UUID idTransporte) {
        Transporte transporte = buscarPorId(idTransporte);
        transporteRepository.delete(transporte);
    }

    private void aplicarDados(Transporte transporte, TransporteRequest request) {
        transporte.setPlaca(request.placa());
        transporte.setModelo(request.modelo());
        transporte.setCapacidade(request.capacidade());
    }
}
