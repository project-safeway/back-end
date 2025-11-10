package com.safeway.tech.services;

import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.models.*;
import com.safeway.tech.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final EnderecoRepository enderecoRepository;
    private final EscolaRepository escolaRepository;
    private final TransporteRepository transporteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public Long cadastrarAlunoCompleto(CadastroAlunoCompletoRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);

        // 1. Criar o aluno
        Aluno aluno = new Aluno();
        aluno.setUsuario(usuario);
        aluno.setNome(request.nome());
        aluno.setProfessor(request.professor());
        aluno.setDtNascimento(request.dtNascimento());
        aluno.setSerie(request.serie());
        aluno.setSala(request.sala());
        aluno.setValorMensalidade(request.valorMensalidade());
        aluno.setDiaVencimento(request.diaVencimento());
        aluno.setAtivo(true);

        // 2. Buscar escola do próprio usuário
        Escola escola = escolaRepository.findByIdEscolaAndUsuario_IdUsuario(request.fkEscola(), userId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));
        aluno.setEscola(escola);

        // 3. Transporte (opcional) do próprio usuário
        if (request.fkTransporte() != null) {
            Transporte transporte = transporteRepository.findByIdTransporteAndUsuario_IdUsuario(request.fkTransporte(), userId)
                    .orElseThrow(() -> new RuntimeException("Transporte não encontrado para este usuário"));
            aluno.setTransporte(transporte);
        }

        aluno = alunoRepository.save(aluno);

        // 4. Processar responsáveis e endereços
        var responsaveis = request.responsaveis();
        if (responsaveis != null) {
            for (CadastroAlunoCompletoRequest.ResponsavelComEnderecoData respData : responsaveis) {
                // 4.1 Criar endereço
                Endereco endereco = new Endereco();
                endereco.setLogradouro(respData.endereco().logradouro());
                endereco.setNumero(respData.endereco().numero());
                endereco.setComplemento(respData.endereco().complemento());
                endereco.setBairro(respData.endereco().bairro());
                endereco.setCidade(respData.endereco().cidade());
                endereco.setCep(respData.endereco().cep());
                endereco = enderecoRepository.save(endereco);

                // 4.2 Criar responsável com vínculo ao usuário
                Responsavel responsavel = new Responsavel();
                responsavel.setUsuario(usuario);
                responsavel.setNome(respData.nome());
                responsavel.setCpf(respData.cpf());
                responsavel.setTel1(respData.tel1());
                responsavel.setTel2(respData.tel2());
                responsavel.setEmail(respData.email());
                responsavel.setEndereco(endereco);
                responsavel = responsavelRepository.save(responsavel);

                // 4.3 Vincular aluno <-> responsável
                responsavel.getAlunos().add(aluno);
                aluno.getResponsaveis().add(responsavel);
            }
        }

        return aluno.getIdAluno();
    }

    public Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }
}