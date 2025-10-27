package com.safeway.tech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mensalidades")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Mensalidade extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensalidade;

    @ManyToOne
    @JoinColumn(name = "fkAluno", nullable = false)
    private Aluno aluno;

    private Date dataMensalidade;
    private BigDecimal valorMensalidade;
    private Boolean pagamentoEfetuado = false;
}

