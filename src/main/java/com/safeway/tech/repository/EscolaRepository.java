package com.safeway.tech.repository;

import com.safeway.tech.models.Escola;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EscolaRepository extends JpaRepository<Escola,Long> {
    List<Escola> findByUsuarioIdUsuario(Long usuarioId);
    Optional<Escola> findByIdEscolaAndUsuario_IdUsuario(Long idEscola, Long userId);
}
