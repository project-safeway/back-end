package com.safeway.tech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.safeway.tech.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "mensalidades_aluno")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MensalidadeAluno extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkAluno", nullable = false)
    private Aluno aluno;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false)
    private Double valorMensalidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPagamento status;

    private LocalDate dataPagamento;

    private Double valorPago;
}