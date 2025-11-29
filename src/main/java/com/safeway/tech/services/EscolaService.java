package com.safeway.tech.services;

import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.dto.EscolaComAlunosResponse;
import com.safeway.tech.dto.EscolaRequest;
import com.safeway.tech.dto.EscolaResponse;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Escola;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.EscolaRepository;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.UsuarioRepository;
import com.safeway.tech.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EscolaService {
    private final EscolaRepository escolaRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final CurrentUserService currentUserService;
    private final EnderecoService enderecoService;

    @Transactional
    public EscolaResponse cadastrarEscola(EscolaRequest request) {
        Long usuarioId = new CurrentUserService().getCurrentUserId();

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Criar endereço
        Endereco endereco = new Endereco();
        endereco.setLogradouro(request.endereco().logradouro());
        endereco.setNumero(request.endereco().numero());
        endereco.setComplemento(request.endereco().complemento());
        endereco.setBairro(request.endereco().bairro());
        endereco.setCidade(request.endereco().cidade());
        endereco.setUf(request.endereco().uf());
        endereco.setCep(request.endereco().cep());
        endereco.setLatitude(request.endereco().latitude());
        endereco.setLongitude(request.endereco().longitude());
        endereco.setTipo("ESCOLA");
        endereco.setAtivo(true);
        endereco.setPrincipal(true);
        endereco = enderecoService.calcularCoordenadas(endereco);
        endereco = enderecoRepository.save(endereco);

        // Criar escola
        Escola escola = new Escola();
        escola.setUsuario(usuario);
        escola.setEndereco(endereco);
        escola.setNome(request.nome());
        escola.setNivelEnsino(request.nivelEnsino());

        escola = escolaRepository.save(escola);

        return EscolaResponse.fromEntity(escola);
    }

    public List<EscolaComAlunosResponse> listarEscolasComAlunos() {
        Long usuarioId = new CurrentUserService().getCurrentUserId();

        return escolaRepository.findByUsuarioIdUsuario(usuarioId).stream()
                .map(EscolaComAlunosResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public EscolaResponse buscarPorId(Long escolaId) {
        Long usuarioId = currentUserService.getCurrentUserId();
        Escola escola = escolaRepository.findByIdEscolaAndUsuario_IdUsuario(escolaId, usuarioId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));
        return EscolaResponse.fromEntity(escola);
    }

    @Transactional(readOnly = true)
    public EnderecoResponse buscarEnderecoDaEscola(Long escolaId) {
        Long usuarioId = currentUserService.getCurrentUserId();
        Escola escola = escolaRepository.findByIdEscolaAndUsuario_IdUsuario(escolaId, usuarioId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));
        Endereco endereco = escola.getEndereco();
        return EnderecoResponse.fromEntity(endereco);
    }

    @Transactional(readOnly = true)
    public Escola buscarEntidadePorId(Long itinerarioId, Long escolaId) {
        Long usuarioId = currentUserService.getCurrentUserId();
        return escolaRepository.findByIdEscolaAndUsuario_IdUsuario(escolaId, usuarioId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));
    }

    @Transactional
    public EscolaResponse atualizarEscola(Long escolaId, EscolaRequest request) {
        Long usuarioId = currentUserService.getCurrentUserId();
        Escola escolaExistente = escolaRepository.findByIdEscolaAndUsuario_IdUsuario(escolaId, usuarioId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));

        escolaExistente.setNome(request.nome());
        escolaExistente.setNivelEnsino(request.nivelEnsino());

        if(request.endereco() != null) {
            Endereco enderecoExistente = escolaExistente.getEndereco();
            enderecoExistente.setLogradouro(request.endereco().logradouro());
            enderecoExistente.setNumero(request.endereco().numero());
            enderecoExistente.setComplemento(request.endereco().complemento());
            enderecoExistente.setBairro(request.endereco().bairro());
            enderecoExistente.setCidade(request.endereco().cidade());
            enderecoExistente.setUf(request.endereco().uf());
            enderecoExistente.setCep(request.endereco().cep());
            enderecoExistente = enderecoService.calcularCoordenadas(enderecoExistente);

            enderecoRepository.save(enderecoExistente);
        }

        escolaRepository.save(escolaExistente);
        return EscolaResponse.fromEntity(escolaExistente);
    }

    @Transactional
    public void deletarEscola(Long escolaId) {
        Long usuarioId = currentUserService.getCurrentUserId();
        Escola escolaExistente = escolaRepository.findByIdEscolaAndUsuario_IdUsuario(escolaId, usuarioId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));

        // Verifica se existem alunos vinculados a esta escola para este usuário
        boolean existeAluno = alunoRepository.existsByEscola_IdEscolaAndUsuario_IdUsuario(escolaId, usuarioId);
        if (existeAluno) {
            throw new RuntimeException("Não é possível excluir a escola, pois existem alunos vinculados a ela.");
        }

        escolaRepository.delete(escolaExistente);
    }
}