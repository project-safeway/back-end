package com.safeway.tech.repository;

import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioEscola;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItinerarioEscolaRepository extends JpaRepository<ItinerarioEscola, Long> {

    Optional<ItinerarioEscola> findByItinerarioIdAndEscolaIdEscola(Long itinerarioId, Long escolaId);

    List<ItinerarioEscola> findByItinerarioId(Long itinerarioId);

    List<ItinerarioEscola> findByItinerarioOrderByOrdemParadaAsc(Itinerario itinerario);
}

