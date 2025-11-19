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


public interface ChamadaRepository extends JpaRepository<Chamada, Long>, JpaSpecificationExecutor<Chamada> {

    @Query("SELECT c FROM Chamada c WHERE c.itinerario.id = :idItinerario AND c.status = :status")
    Optional<Chamada> findByItinerarioIdAndStatus(
            @Param("idItinerario") Long idItinerario,
            @Param("status") StatusChamadaEnum status
    );

    @Query("SELECT c FROM Chamada c WHERE c.itinerario.id = :idItinerario")
    Page<Chamada> findAllByItinerarioId(Long idItinerario, Pageable pageable);
}
