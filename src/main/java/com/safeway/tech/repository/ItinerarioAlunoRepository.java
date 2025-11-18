package com.safeway.tech.repository;

import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItinerarioAlunoRepository extends JpaRepository<ItinerarioAluno, Long> {

    @Query("SELECT ia FROM ItinerarioAluno ia WHERE ia.itinerario.id = :itinerarioId")
    List<ItinerarioAluno> findByItinerarioId(@Param("itinerarioId") Long itinerarioId);

    @Query("SELECT ia FROM ItinerarioAluno ia WHERE ia.itinerario.id = :itinerarioId AND ia.aluno.idAluno = :alunoId")
    Optional<ItinerarioAluno> findByItinerarioIdAndAlunoId(
            @Param("itinerarioId") Long itinerarioId,
            @Param("alunoId") Long alunoId);

    @Query("DELETE FROM ItinerarioAluno ia WHERE ia.itinerario.id = :itinerarioId")
    void deleteAllByItinerarioId(
            @Param("itinerarioId") Long itinerarioId);

    List<ItinerarioAluno> findByItinerarioOrderByOrdemEmbarqueAsc(Itinerario itinerario);
}
