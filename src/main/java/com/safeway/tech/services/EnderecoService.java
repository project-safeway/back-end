package com.safeway.tech.services;

import com.safeway.tech.dto.EnderecoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.repository.AlunoRepository;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Transactional
    public EnderecoResponse criar(EnderecoRequest request) {
        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Responsavel responsavel = null;
        if (request.responsavelId() != null) {
            responsavel = responsavelRepository.findById(request.responsavelId())
                    .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
        }

        if (Boolean.TRUE.equals(request.principal())) {
            desmarcarOutrosPrincipais(request.alunoId());
        }

        Endereco endereco = new Endereco();
        endereco.setAluno(aluno);
        endereco.setResponsavel(responsavel);
        endereco.setLogradouro(request.logradouro());
        endereco.setNumero(request.numero());
        endereco.setComplemento(request.complemento());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setUf(request.uf());
        endereco.setCep(request.cep());
        endereco.setLatitude(request.latitude());
        endereco.setLongitude(request.longitude());
        endereco.setTipo(request.tipo());
        endereco.setAtivo(true);
        endereco.setPrincipal(Boolean.TRUE.equals(request.principal()));

        endereco = enderecoRepository.save(endereco);
        return EnderecoResponse.fromEntity(endereco);
    }

    public List<EnderecoResponse> listarPorAluno(Long alunoId) {
        return enderecoRepository.findByAlunoIdAlunoAndAtivoTrue(alunoId).stream()
                .map(EnderecoResponse::fromEntity)
                .toList();
    }

    public EnderecoResponse buscarPorId(Long id) {
        Endereco endereco = buscarEntidade(id);
        return EnderecoResponse.fromEntity(endereco);
    }

    @Transactional
    public EnderecoResponse atualizar(Long id, EnderecoRequest request) {
        Endereco endereco = buscarEntidade(id);

        if (request.responsavelId() != null) {
            Responsavel responsavel = responsavelRepository.findById(request.responsavelId())
                    .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
            endereco.setResponsavel(responsavel);
        }

        if (Boolean.TRUE.equals(request.principal())) {
            desmarcarOutrosPrincipais(endereco.getAluno().getIdAluno());
        }

        endereco.setLogradouro(request.logradouro());
        endereco.setNumero(request.numero());
        endereco.setComplemento(request.complemento());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setUf(request.uf());
        endereco.setCep(request.cep());
        endereco.setLatitude(request.latitude());
        endereco.setLongitude(request.longitude());
        endereco.setTipo(request.tipo());
        endereco.setPrincipal(Boolean.TRUE.equals(request.principal()));

        endereco = enderecoRepository.save(endereco);
        return EnderecoResponse.fromEntity(endereco);
    }

    @Transactional
    public void desativar(Long id) {
        Endereco endereco = buscarEntidade(id);
        endereco.setAtivo(false);
        enderecoRepository.save(endereco);
    }

    @Transactional
    public void definirComoPrincipal(Long id) {
        Endereco endereco = buscarEntidade(id);
        desmarcarOutrosPrincipais(endereco.getAluno().getIdAluno());
        endereco.setPrincipal(true);
        enderecoRepository.save(endereco);
    }

    public Endereco buscarEntidade(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    private void desmarcarOutrosPrincipais(Long alunoId) {
        enderecoRepository.findByAlunoIdAlunoAndAtivoTrue(alunoId).stream()
                .filter(Endereco::getPrincipal)
                .forEach(e -> {
                    e.setPrincipal(false);
                    enderecoRepository.save(e);
                });
    }
}