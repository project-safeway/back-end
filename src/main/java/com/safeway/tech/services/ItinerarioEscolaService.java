package com.safeway.tech.services;

import com.safeway.tech.dto.ItinerarioEscolaRequest;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Escola;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioEscola;
import com.safeway.tech.repository.ItinerarioEscolaRepository;
import com.safeway.tech.repository.ItinerarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItinerarioEscolaService {

    private final ItinerarioEscolaRepository itinerarioEscolaRepository;
    private final ItinerarioRepository itinerarioRepository;
    private final EscolaService escolaService;
    private final EnderecoService enderecoService;

    @Transactional
    public void adicionarEscola(UUID itinerarioId, ItinerarioEscolaRequest request) {
        Itinerario itinerario = itinerarioRepository.findById(itinerarioId)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));

        Escola escola = escolaService.buscarEntidadePorId(itinerarioId, request.escolaId());

        Endereco endereco;
        if (request.enderecoId() != null) {
            endereco = enderecoService.buscarEntidade(request.enderecoId());
        } else {
            endereco = escola.getEndereco();
        }

        if (endereco.getLatitude() == null || endereco.getLongitude() == null) {
            throw new RuntimeException("Endereço da escola não possui latitude/longitude válidas");
        }
        double lat = endereco.getLatitude();
        double lng = endereco.getLongitude();
        if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
            throw new RuntimeException("Coordenadas do endereço da escola inválidas: " + lat + ", " + lng);
        }

        itinerarioEscolaRepository.findByItinerarioIdAndEscolaIdEscola(itinerarioId, escola.getId())
                .ifPresent(e -> { throw new RuntimeException("Escola já está vinculada a este itinerário"); });

        ItinerarioEscola entity = new ItinerarioEscola();
        entity.setItinerario(itinerario);
        entity.setEscola(escola);
        entity.setEndereco(endereco);
        entity.setOrdemParada(request.ordemParada());

        itinerarioEscolaRepository.save(entity);
    }

    @Transactional
    public void removerEscola(UUID itinerarioId, UUID escolaId) {
        ItinerarioEscola entity = itinerarioEscolaRepository
                .findByItinerarioIdAndEscolaIdEscola(itinerarioId, escolaId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada no itinerário"));

        itinerarioEscolaRepository.delete(entity);
    }

    @Transactional
    public void reordenar(UUID itinerarioId, List<UUID> novaOrdemEscolaIds) {
        List<ItinerarioEscola> atuais = itinerarioEscolaRepository.findByItinerarioId(itinerarioId);

        Map<UUID, ItinerarioEscola> map = atuais.stream()
                .collect(Collectors.toMap(e -> e.getEscola().getId(), e -> e));

        int ordem = 1;
        for (UUID id : novaOrdemEscolaIds) {
            ItinerarioEscola ie = map.get(id);
            if (ie != null) {
                ie.setOrdemParada(ordem++);
            }
        }

        itinerarioEscolaRepository.saveAll(atuais);
    }
}
