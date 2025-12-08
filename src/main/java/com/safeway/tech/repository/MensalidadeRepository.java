package com.safeway.tech.repository;

import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.MensalidadeAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface MensalidadeRepository extends JpaRepository<MensalidadeAluno, Long>, JpaSpecificationExecutor<MensalidadeAluno> {

    List<MensalidadeAluno> findByStatusAndDataVencimentoBefore(
            StatusPagamento status,
            LocalDate data
    );

    boolean existsByAlunoAndDataVencimentoBetween(
            Aluno aluno,
            LocalDate dataInicio,
            LocalDate dataFim);

}
