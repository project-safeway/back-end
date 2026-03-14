package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.aluno.AlunoRequest;
import com.safeway.tech.api.dto.responsavel.ResponsavelRequest;
import com.safeway.tech.domain.models.Aluno;
import com.safeway.tech.domain.models.Escola;
import com.safeway.tech.domain.models.Responsavel;
import com.safeway.tech.domain.models.Transporte;
import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.infra.exception.AlunoNotFoundException;
import com.safeway.tech.infra.exception.OperationNotAllowedException;
import com.safeway.tech.infra.messaging.publishers.EventPublisher;
import com.safeway.tech.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final EscolaService escolaService;
    private final UsuarioService usuarioService;
    private final ResponsavelService responsavelService;
    private final TransporteService transporteService;
    private final EventPublisher eventPublisher;
    private final CurrentUserService currentUserService;

    public Aluno buscarPorId(UUID alunoId) {
        UUID userId = currentUserService.getCurrentUserId();
        return alunoRepository.findByIdAndUsuarioId(alunoId, userId)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado"));
    }

    @Transactional
    public Aluno criarAluno(AlunoRequest request) {
        UUID userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.buscarPorId(userId);

        if (!usuario.getAtivo()) {
            throw new OperationNotAllowedException("O usuário não possúi permissão para realizar esta operação");
        }

        UUID transporteId = currentUserService.getCurrentTransporteId();
        Transporte transporte = transporteService.getById(transporteId);

        Escola escola = escolaService.buscarPorId(request.escolaId());

        Aluno aluno = new Aluno();
        aplicarDados(aluno, request);
        aluno.setEscola(escola);
        aluno.setUsuario(usuario);
        aluno.setTransporte(transporte);

        aluno = alunoRepository.save(aluno);

        for (ResponsavelRequest responsavelRequest : request.responsaveis()) {
            Responsavel responsavel = responsavelService
                    .buscarPorCpfAndUsuario(responsavelRequest.cpf(), userId)
                    .orElseGet(() -> responsavelService.criarResponsavel(responsavelRequest));

            aluno.adicionarResponsavel(responsavel);
        }

        eventPublisher.publicarAlunoCriado(aluno);
        return aluno;
    }

    @Transactional
    public Aluno atualizarAluno(UUID alunoId, AlunoRequest request) {
        UUID userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.buscarPorId(userId);

        if (!usuario.getAtivo()) {
            throw new OperationNotAllowedException("O usuário não possúi permissão para realizar esta operação");
        }

        Escola escola = escolaService.buscarPorId(request.escolaId());

        Aluno aluno = buscarPorId(alunoId);
        aplicarDados(aluno, request);
        aluno.setEscola(escola);

        atualizarResponsaveis(aluno, request.responsaveis(), userId);
        aluno = alunoRepository.save(aluno);

        eventPublisher.publicarAlunoAtualizado(aluno);
        return aluno;
    }

    @Transactional
    public void deletarAluno(UUID alunoId) {
        Aluno aluno = buscarPorId(alunoId);
        aluno.setAtivo(false);
        alunoRepository.save(aluno);
        eventPublisher.publicarAlunoInativado(aluno);
    }

    public List<Aluno> buscarPorIdEmLote(List<UUID> ids) {
        UUID userId = currentUserService.getCurrentUserId();
        return alunoRepository.findByIdInAndIdUsuario(ids, userId);
    }

    public List<Aluno> buscarTodosAtivos() {
        UUID userId = currentUserService.getCurrentUserId();
        return alunoRepository.findByAtivoTrueAndIdUsuario(userId);
    }

    private void aplicarDados(Aluno aluno, AlunoRequest request) {
        aluno.setNome(request.nome());
        aluno.setProfessor(request.professor());
        aluno.setDtNascimento(request.dtNascimento());
        aluno.setSerie(request.serie());
        aluno.setSala(request.sala());
        aluno.setValorMensalidade(request.valorMensalidade());
        aluno.setDiaVencimento(request.diaVencimento());
    }

    private void atualizarResponsaveis(Aluno aluno, List<ResponsavelRequest> requests, UUID userId) {
        List<Responsavel> novosResponsaveis = new ArrayList<>();

        for (ResponsavelRequest request : requests) {
            Responsavel responsavel = responsavelService.buscarPorCpfAndUsuario(request.cpf(), userId)
                    .orElseGet(() -> responsavelService.criarResponsavel(request));
            novosResponsaveis.add(responsavel);
        }

        for (Responsavel responsavelAtual : new ArrayList<>(aluno.getResponsaveis())) {
            if (!novosResponsaveis.contains(responsavelAtual)) {
                aluno.removerResponsavel(responsavelAtual);
            }
        }

        for (Responsavel responsavel : novosResponsaveis) {
            aluno.adicionarResponsavel(responsavel);
        }
    }
}
