package com.safeway.tech.repository;

import com.safeway.tech.models.ItinerarioAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItinerarioAlunoRepository extends JpaRepository<ItinerarioAluno, Long> {

    @Query("SELECT ia FROM ItinerarioAluno ia WHERE ia.itinerario.id = :idItinerario AND ia.ordemEmbarque = :ordemEmbarque")
    ItinerarioAluno findByItinerarioAndOrdemEmbarque(
            @Param("idItinerario") Long idItinerario,
            @Param("ordemEmbarque") Integer ordemEmbarque);

    @Query("SELECT ia FROM ItinerarioAluno ia WHERE ia.itinerario.id = :idItinerario ORDER BY ia.ordemEmbarque ASC")
    List<ItinerarioAluno> findByItinerario(Long idItinerario);
}
