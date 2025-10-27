package com.safeway.tech.models;

import com.safeway.tech.enums.PresencaStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamada_aluno")
@Data
public class ChamadaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChamadaAluno;

    @ManyToOne
    @JoinColumn(name = "fk_chamada", nullable = false)
    private Chamada chamada;

    @ManyToOne
    @JoinColumn(name = "fk_aluno", nullable = false)
    private Aluno aluno;

    private PresencaStatusEnum presenca;

    private LocalDateTime horarioRegistro;
}
