package com.safeway.tech.repository;

import com.safeway.tech.enums.TipoViagemEnum;
import com.safeway.tech.models.Itinerario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.List;

public interface ItinerarioRepository extends JpaRepository<Itinerario, Long> {

    @Query("SELECT i FROM Itinerario i WHERE i.horarioInicio >= :horarioInicio AND i.horarioFim <= :horarioFim AND i.transporte.idTransporte = :idTransporte")
    List<Itinerario> findAllByIntervaloDeHoras(
            @Param("horarioInicio") Time horarioInicio,
            @Param("horarioFim") Time horarioFim,
            @Param("idTransporte") Long idTransporte);

    @Query("SELECT i FROM Itinerario i WHERE i.tipoViagem = :tipoViagem AND i.transporte.idTransporte = :idTransporte")
    List<Itinerario> findAllByTipoViagem(
            @Param("tipoViagem") TipoViagemEnum tipoViagem,
            @Param("idTransporte") Long idTransporte);

    @Query("SELECT DISTINCT i FROM Itinerario i JOIN i.alunos ia WHERE ia.aluno.idAluno = :idAluno AND i.transporte.idTransporte = :idTransporte")
    List<Itinerario> findAllByAluno(
            @Param("idAluno") Long idAluno,
            @Param("idTransporte") Long idTransporte);
}
