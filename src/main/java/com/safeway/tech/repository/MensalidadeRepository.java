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

    boolean existsByAlunoAndDataVencimentoBetween(
            Aluno aluno,
            LocalDate dataInicio,
            LocalDate dataFim);

    @EntityGraph(attributePaths = {"aluno", "aluno.responsaveis"})
    @Query("SELECT m FROM MensalidadeAluno m WHERE m.status IN :statuses AND m.aluno.usuario.idUsuario = :userId")
    List<MensalidadeAluno> findByStatusInWithDetails(@Param("statuses") List<StatusPagamento> statuses,
                                                     @Param("userId") Long userId);

    @EntityGraph(attributePaths = {"aluno", "aluno.responsaveis"})
    @Query("SELECT m FROM MensalidadeAluno m " +
            "WHERE m.dataVencimento BETWEEN :dataInicio AND :dataFinal " +
            "AND m.status IN :statuses AND m.aluno.usuario.idUsuario = :userId")
    List<MensalidadeAluno> findByMesAndAnoAndStatusInWithDetails(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFinal") LocalDate dataFinal,
            @Param("statuses") List<StatusPagamento> statuses,
            @Param("userId") Long userId
    );

    @Query("SELECT m FROM MensalidadeAluno m WHERE m.aluno.usuario.idUsuario = :userId")
    List<MensalidadeAluno> findByUserId(@Param("userId") Long userId);
}
