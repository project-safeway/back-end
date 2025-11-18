package com.safeway.tech.models;

import com.safeway.tech.enums.StatusPresencaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamada_aluno")
@Data
@EqualsAndHashCode
public class ChamadaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Chamada chamada;

    @ManyToOne(fetch = FetchType.LAZY)
    private Aluno aluno;

    private StatusPresencaEnum presenca;

    private LocalDateTime data;
}
