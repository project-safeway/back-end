package com.safeway.tech.repository;

import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.Mensalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensalidadeRepository extends JpaRepository<Mensalidade, Long> {
    List<Mensalidade> findByAlunoTransporte(Itinerario itinerario);

    // Tem que corrigir essa query
    @Query("SELECT m FROM Mensalidade m WHERE : m.alunoTransporte.transporte.usuarios")
    List<Mensalidade> findAllByUsuario(Long id);
}
