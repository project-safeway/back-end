package com.safeway.tech.repository;

import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioEscola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItinerarioEscolaRepository extends JpaRepository<ItinerarioEscola, UUID> {

    @Query("SELECT ie FROM ItinerarioEscola ie WHERE ie.itinerario.id = :itinerarioId AND ie.escola.id = :escolaId")
    Optional<ItinerarioEscola> findByItinerarioIdAndEscolaIdEscola(@Param("itinerarioId") UUID itinerarioId, @Param("escolaId") UUID escolaId);

    @Query("SELECT ie FROM ItinerarioEscola ie WHERE ie.itinerario.id = :itinerarioId")
    List<ItinerarioEscola> findByItinerarioId(@Param("itinerarioId") UUID itinerarioId);
}

