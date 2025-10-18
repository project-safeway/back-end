package com.safeway.tech.repository;

import com.safeway.tech.models.Mensalidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensalidadeRepository extends JpaRepository<Mensalidade,Long> {
}
