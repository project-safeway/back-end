package com.safeway.tech.repository;

import com.safeway.tech.models.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel,Long> {
    List<Responsavel> findAllByUsuario_IdUsuario(Long userId);
    List<Responsavel> findByAlunosIdAlunoAndUsuarioIdUsuario(Long alunoId, Long usuarioId);
    Optional<Responsavel> findByIdResponsavelAndUsuario_IdUsuario(Long idResponsavel, Long usuarioId);
    Optional<Responsavel> findByCpfAndUsuario_IdUsuario(String cpf, Long usuarioId);
}
