package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.escola.EscolaRequest;
import com.safeway.tech.domain.models.Endereco;
import com.safeway.tech.domain.models.Escola;
import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.infra.exception.EscolaNotFoundException;
import com.safeway.tech.repository.EscolaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EscolaService {

    private final EscolaRepository escolaRepository;
    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;
    private final CurrentUserService currentUserService;

    public List<Escola> listarEscolasComAlunos() {
        UUID usuarioId = new CurrentUserService().getCurrentUserId();
        return escolaRepository.findByUsuarioIdUsuario(usuarioId);
    }

    @Transactional(readOnly = true)
    public Escola buscarPorId(UUID escolaId) {
        UUID usuarioId = currentUserService.getCurrentUserId();
        return escolaRepository.findByIdEscolaAndIdUsuario(escolaId, usuarioId)
                .orElseThrow(() -> new EscolaNotFoundException("Escola não encontrada"));
    }

    @Transactional(readOnly = true)
    public Endereco buscarEnderecoDaEscola(UUID escolaId) {
        UUID usuarioId = currentUserService.getCurrentUserId();
        Escola escola = escolaRepository.findByIdEscolaAndIdUsuario(escolaId, usuarioId)
                .orElseThrow(() -> new EscolaNotFoundException("Escola não encontrada"));
        return escola.getEndereco();
    }

    @Transactional
    public Escola cadastrarEscola(EscolaRequest request) {
        Escola escola = new Escola();
        aplicarDados(escola, request);

        Endereco endereco = enderecoService.criar(request.endereco());
        escola.setEndereco(endereco);

        UUID usuarioId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        escola.setUsuario(usuario);

        return escolaRepository.save(escola);
    }

    @Transactional
    public Escola atualizarEscola(UUID escolaId, EscolaRequest request) {
        Escola escola = buscarPorId(escolaId);

        aplicarDados(escola, request);
        Endereco endereco = enderecoService.atualizar(escola.getEndereco().getId(), request.endereco());
        escola.setEndereco(endereco);

        return escolaRepository.save(escola);
    }

    @Transactional
    public void desativar(UUID escolaId) {
        Escola escola = buscarPorId(escolaId);
        escola.setAtivo(false);
        escolaRepository.save(escola);
    }

    private void aplicarDados(Escola escola, EscolaRequest request) {
        escola.setNome(request.nome());
        escola.setNivelEnsino(request.nivelEnsino());
    }
}
