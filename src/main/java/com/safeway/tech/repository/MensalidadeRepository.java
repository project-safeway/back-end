package com.safeway.tech.repository;

import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.MensalidadeAluno;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MensalidadeRepository extends JpaRepository<MensalidadeAluno, Long> {

    List<MensalidadeAluno> findByStatusAndDataVencimentoBefore(
            StatusPagamento status,
            LocalDate data
    );

    boolean existsByAlunoAndMesAndAno(Aluno aluno, Integer mes, Integer ano);

    @EntityGraph(attributePaths = {"aluno", "aluno.responsaveis"})
    @Query("SELECT m FROM MensalidadeAluno m WHERE m.status IN :statuses")
    List<MensalidadeAluno> findByStatusInWithDetails(@Param("statuses") List<StatusPagamento> statuses);

    @EntityGraph(attributePaths = {"aluno", "aluno.responsaveis"})
    @Query("SELECT m FROM MensalidadeAluno m " +
            "WHERE m.mes = :mes AND m.ano = :ano AND m.status IN :statuses")
    List<MensalidadeAluno> findByMesAndAnoAndStatusInWithDetails(
            @Param("mes") Integer mes,
            @Param("ano") Integer ano,
            @Param("statuses") List<StatusPagamento> statuses
    );
}
