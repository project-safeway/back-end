package com.safeway.tech.services;

import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Escola;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.repository.AlunoRepository;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.EscolaRepository;
import com.safeway.tech.repository.ResponsavelRepository;
import com.safeway.tech.repository.TransporteRepository;
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

    @Transactional
    public Long cadastrarAlunoCompleto(CadastroAlunoCompletoRequest request) {
        // 1. Criar o aluno
        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setProfessor(request.professor());
        aluno.setDtNascimento(request.dtNascimento());
        aluno.setSerie(request.serie());
        aluno.setSala(request.sala());
        aluno.setValorMensalidade(request.valorMensalidade());
        aluno.setDiaVencimento(request.diaVencimento());
        aluno.setAtivo(true);

        // 2. Buscar escola
        Escola escola = escolaRepository.findById(request.fkEscola())
                .orElseThrow(() -> new RuntimeException("Escola não encontrada"));
        aluno.setEscola(escola);

        // 3. Transporte (opcional)
        if (request.fkTransporte() != null) {
            Transporte transporte = transporteRepository.findById(request.fkTransporte())
                    .orElseThrow(() -> new RuntimeException("Transporte não encontrado"));
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

                // 4.2 Criar responsável
                Responsavel responsavel = new Responsavel();
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
}