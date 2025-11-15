package com.safeway.tech.repository;

import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.MensalidadeAluno;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MensalidadeRepository extends JpaRepository<MensalidadeAluno, Long>, JpaSpecificationExecutor<MensalidadeAluno> {

    List<MensalidadeAluno> findByStatusAndDataVencimentoBefore(
            StatusPagamento status,
            LocalDate data
    );

    @Query("SELECT m FROM MensalidadeAluno m WHERE m.aluno = :aluno AND m.dataVencimento BETWEEN :dataInicio AND :dataFim")
    Boolean existsByAlunoAndDataVencimentoBetween(
            Aluno aluno,
            LocalDate dataInicio,
            LocalDate dataFim);

}
