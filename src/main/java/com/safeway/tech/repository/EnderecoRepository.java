package com.safeway.tech.repository;

import com.safeway.tech.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByAlunoIdAlunoAndAtivoTrue(Long alunoId);
    List<Endereco> findByResponsavelIdResponsavel(Long responsavelId);
    Optional<Endereco> findByAlunoIdAlunoAndPrincipalTrue(Long alunoId);
}
