package com.safeway.tech.repository;

import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.models.Chamada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface ChamadaRepository extends JpaRepository<Chamada, UUID>, JpaSpecificationExecutor<Chamada> {

    @Query("SELECT c FROM Chamada c WHERE c.itinerario.id = :idItinerario AND c.status = :status")
    Optional<Chamada> findByItinerarioIdAndStatus(
            @Param("idItinerario") UUID idItinerario,
            @Param("status") StatusChamadaEnum status
    );
}
