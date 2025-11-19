package com.safeway.tech.services;

import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.AlunoRepository;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.ResponsavelRepository;
import com.safeway.tech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResponsavelService {
    private final ResponsavelRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final EnderecoRepository enderecoRepository;
    private final CurrentUserService currentUserService;

    private Responsavel getOwnedOrThrow(Long id){
        Long userId = currentUserService.getCurrentUserId();
        return repository.findByIdResponsavelAndUsuario_IdUsuario(id, userId)
                .orElseThrow(RuntimeException::new);
    }

    public Responsavel getById(Long id){
        return getOwnedOrThrow(id);
    }

    public Responsavel salvarResponsavel(Responsavel responsavel){
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);
        responsavel.setUsuario(usuario);

        // Endereco sem cascade precisa ser salvo explícito
        Endereco end = responsavel.getEndereco();
        if (end != null && end.getIdEndereco() == null) {
            end = enderecoRepository.save(end);
            responsavel.setEndereco(end);
        }

        // Re-hidrata a lista de alunos garantindo que pertencem ao usuário
        if (responsavel.getAlunos() != null && !responsavel.getAlunos().isEmpty()) {
            List<Aluno> validados = responsavel.getAlunos().stream()
                    .map(a -> alunoRepository.findByIdAlunoAndUsuario_IdUsuario(a.getIdAluno(), userId)
                            .orElseThrow(() -> new RuntimeException("Aluno não encontrado para este usuário: " + a.getIdAluno())))
                    .toList();
            responsavel.setAlunos(validados);
        }

        return repository.save(responsavel);
    }

    public List<Responsavel> listarResponsaveis() {
        Long userId = currentUserService.getCurrentUserId();
        return repository.findAllByUsuario_IdUsuario(userId);
    }

    public Responsavel retornarUm(Long idResponsavel){
        return getOwnedOrThrow(idResponsavel);
    }

    public void excluir(Long id){
        repository.delete(getOwnedOrThrow(id));
    }

    public Responsavel alterarResponsavel(Responsavel responsavel,Long idResponsavel){
        Long userId = currentUserService.getCurrentUserId();
        Responsavel atual = getOwnedOrThrow(idResponsavel);
        atual.setNome(responsavel.getNome());

        // Atualiza/insere endereço
        Endereco novoEnd = responsavel.getEndereco();
        if (novoEnd != null) {
            Endereco destino = atual.getEndereco();
            if (destino == null || destino.getIdEndereco() == null) {
                destino = new Endereco();
            }
            destino.setLogradouro(novoEnd.getLogradouro());
            destino.setNumero(novoEnd.getNumero());
            destino.setCidade(novoEnd.getCidade());
            destino.setCep(novoEnd.getCep());
            destino = enderecoRepository.save(destino);
            atual.setEndereco(destino);
        }

        if (responsavel.getAlunos() != null) {
            List<Aluno> validados = responsavel.getAlunos().stream()
                    .filter(Objects::nonNull)
                    .map(a -> alunoRepository.findByIdAlunoAndUsuario_IdUsuario(a.getIdAluno(), userId)
                            .orElseThrow(() -> new RuntimeException("Aluno não encontrado para este usuário: " + a.getIdAluno())))
                    .toList();
            atual.setAlunos(validados);
        }

        return repository.save(atual);
    }
}
