package com.safeway.tech.services;

import com.safeway.tech.dto.EnderecoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Transactional
    public EnderecoResponse criar(EnderecoRequest request) {
        if (request.responsavelId() == null) {
            throw new RuntimeException("responsavelId é obrigatório");
        }

        Responsavel responsavel = responsavelRepository.findById(request.responsavelId())
                .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));

        Endereco endereco = new Endereco();
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
        endereco.setPrincipal(false); // Principal é gerenciado no Responsavel

        endereco = enderecoRepository.save(endereco);

        // Vincula endereço ao responsável
        responsavel.setEndereco(endereco);
        responsavelRepository.save(responsavel);

        return EnderecoResponse.fromEntity(endereco);
    }

    public List<EnderecoResponse> listarPorResponsavel(Long responsavelId) {
        Responsavel responsavel = responsavelRepository.findById(responsavelId)
                .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));

        return List.of(EnderecoResponse.fromEntity(responsavel.getEndereco()));
    }

    @Transactional
    public List<EnderecoResponse> listarEnderecosDisponiveis(Long alunoId, Long usuarioId) {
        // Busca endereços de todos os responsáveis vinculados ao aluno
        List<Responsavel> responsaveis = responsavelRepository.findByAlunosIdAlunoAndUsuarioIdUsuario(alunoId, usuarioId);

        return responsaveis.stream()
                .map(r -> {
                    Endereco e = r.getEndereco();
                    if (e == null) return null;
                    // Acessar um campo para garantir inicialização dentro da transação
                    e.getIdEndereco();
                    return EnderecoResponse.fromEntity(e);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public EnderecoResponse buscarPorId(Long id) {
        Endereco endereco = buscarEntidade(id);
        return EnderecoResponse.fromEntity(endereco);
    }

    @Transactional
    public EnderecoResponse atualizar(Long id, EnderecoRequest request) {
        Endereco endereco = buscarEntidade(id);

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

        endereco = enderecoRepository.save(endereco);
        return EnderecoResponse.fromEntity(endereco);
    }

    @Transactional
    public void desativar(Long id) {
        Endereco endereco = buscarEntidade(id);
        endereco.setAtivo(false);
        enderecoRepository.save(endereco);
    }

    public Endereco buscarEntidade(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }
}