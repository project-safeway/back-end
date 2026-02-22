package com.safeway.tech.models;

import com.safeway.tech.enums.StatusPresencaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamada_aluno")
@Getter @Setter
@NoArgsConstructor
public class ChamadaAluno extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Chamada chamada;

    @ManyToOne(fetch = FetchType.LAZY)
    private Aluno aluno;

    private StatusPresencaEnum presenca;

    private LocalDateTime data;
}
