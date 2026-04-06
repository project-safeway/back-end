package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.responsavel.ResponsavelRequest;
import com.safeway.tech.domain.models.Endereco;
import com.safeway.tech.domain.models.Responsavel;
import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.infra.exception.ResponsavelNotFoundException;
import com.safeway.tech.repository.ResponsavelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private final CurrentUserService currentUserService;

    public Responsavel buscarPorId(UUID id) {
        UUID userId = currentUserService.getCurrentUserId();
        return responsavelRepository.findByIdResponsavelAndIdUsuario(id, userId)
                .orElseThrow(() -> new ResponsavelNotFoundException("O responsável com ID " + id + "não foi encontrado"));
    }

    public Optional<Responsavel> buscarPorCpfAndUsuario(String cpf, UUID userId) {
        return responsavelRepository.findByCpfAndIdUsuario(cpf, userId);
    }

    public List<Responsavel> listarResponsaveis() {
        UUID userId = currentUserService.getCurrentUserId();
        return responsavelRepository.findAllByIdUsuario(userId);
    }

    public List<Responsavel> listarResponsaveisPorAluno(UUID alunoId) {
        UUID userId = currentUserService.getCurrentUserId();
        return responsavelRepository.findByAlunosIdAndUsuarioIdUsuario(alunoId, userId);
    }

    @Transactional
    public Responsavel criarResponsavel(ResponsavelRequest request) {
        Responsavel responsavel = new Responsavel();
        aplicaDados(responsavel, request);

        responsavel = responsavelRepository.save(responsavel);

        Endereco endereco = enderecoService.criar(request.endereco());
        responsavel.setEndereco(endereco);

        UUID userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.buscarPorId(userId);
        responsavel.setUsuario(usuario);

        return responsavelRepository.save(responsavel);
    }

    public Responsavel alterarResponsavel(ResponsavelRequest request, UUID idResponsavel) {
        Responsavel responsavel = buscarPorId(idResponsavel);
        aplicaDados(responsavel, request);
        return responsavelRepository.save(responsavel);
    }

    public void desativar(UUID id) {
        Responsavel responsavel = buscarPorId(id);
        responsavel.setAtivo(false);
        responsavelRepository.save(responsavel);
    }

    private void aplicaDados(Responsavel responsavel, ResponsavelRequest request) {
        responsavel.setNome(request.nome());
        responsavel.setCpf(request.cpf());
        responsavel.setTel1(request.tel1());
        responsavel.setTel2(request.tel2());
        responsavel.setEmail(request.email());
    }
}
