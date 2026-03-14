package com.safeway.tech.service.services;

import com.google.maps.model.LatLng;
import com.safeway.tech.api.dto.endereco.EnderecoRequest;
import com.safeway.tech.domain.models.Endereco;
import com.safeway.tech.domain.models.Responsavel;
import com.safeway.tech.infra.exception.EnderecoNotFoundException;
import com.safeway.tech.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ResponsavelService responsavelService;
    private final GeocodingService geocodingService;

    public Endereco buscarPorId(UUID id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereço com ID " + id + " não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Endereco> listarEnderecosDisponiveis(UUID alunoId) {
        List<Responsavel> responsaveis = responsavelService.listarResponsaveisPorAluno(alunoId);

        return responsaveis.stream()
                .map(Responsavel::getEndereco)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public Endereco criar(EnderecoRequest request) {
        Endereco endereco = new Endereco();

        aplicarDados(endereco, request);

        calcularCoordenadas(endereco);
        return enderecoRepository.save(endereco);
    }

    public Endereco atualizar(UUID id, EnderecoRequest request) {
        Endereco endereco = buscarPorId(id);

        aplicarDados(endereco, request);
        calcularCoordenadas(endereco);

        return enderecoRepository.save(endereco);
    }

    public void desativar(UUID id) {
        Endereco endereco = buscarPorId(id);
        endereco.setAtivo(false);
        enderecoRepository.save(endereco);
    }

    private void aplicarDados(Endereco endereco, EnderecoRequest request) {
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
    }

    private void calcularCoordenadas(Endereco endereco) {
        String enderecoCompleto = String.format("%s, %s, %s, %s, %s, %s",
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep()
        );

        LatLng coordenadas = geocodingService.obterCoordenadas(enderecoCompleto);
        endereco.setLatitude(coordenadas.lat);
        endereco.setLongitude(coordenadas.lng);
    }

}
