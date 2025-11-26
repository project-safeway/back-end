package com.safeway.tech.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safeway.tech.dto.EnderecoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    public Endereco calcularCoordenadas(Endereco endereco) {
        try {
            String street = endereco.getLogradouro() + " " + endereco.getNumero();

            String url = UriComponentsBuilder
                    .fromHttpUrl("https://nominatim.openstreetmap.org/search")
                    .queryParam("street", street)
                    .queryParam("city", endereco.getCidade())
                    .queryParam("state", endereco.getUf())
                    .queryParam("country", "Brasil")
                    .queryParam("postalcode", endereco.getCep().replace("-", ""))
                    .queryParam("format", "json")
                    .queryParam("limit", 1)
                    .build()
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            if (response != null && !response.equals("[]")) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response);

                if (root.isArray() && root.size() > 0) {
                    JsonNode firstResult = root.get(0);
                    Double lat = firstResult.get("lat").asDouble();
                    Double lon = firstResult.get("lon").asDouble();

                    endereco.setLatitude(lat);
                    endereco.setLongitude(lon);
                }
            }

            return endereco;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular coordenadas: " + e.getMessage());
        }
    }

}