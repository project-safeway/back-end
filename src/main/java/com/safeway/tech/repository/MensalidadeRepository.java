package com.safeway.tech.repository;

import com.safeway.tech.models.AlunoTransporte;
import com.safeway.tech.models.Mensalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensalidadeRepository extends JpaRepository<Mensalidade, Long> {
    List<Mensalidade> findByAlunoTransporte(AlunoTransporte alunoTransporte);

    @Query("SELECT m FROM Mensalidade m WHERE : m.alunoTransporte.transporte.usuarios")
    List<Mensalidade> findAllByUsuario(Long id);
}
