package com.safeway.tech.repository;

import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Chamada;
import com.safeway.tech.models.ChamadaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ChamadaAlunoRepository extends JpaRepository<ChamadaAluno, UUID> {

    @Query("SELECT ca FROM ChamadaAluno ca WHERE ca.chamada = :chamada AND ca.aluno = :aluno")
    Optional<ChamadaAluno> findByChamadaAndAluno(
            @Param("chamada") Chamada chamada,
            @Param("aluno") Aluno aluno);
}
