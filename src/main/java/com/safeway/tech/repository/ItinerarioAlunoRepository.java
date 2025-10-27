package com.safeway.tech.repository;

import com.safeway.tech.models.ItinerarioAluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItinerarioAlunoRepository extends JpaRepository<ItinerarioAluno, Long> {
}
