package com.safeway.tech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Aluno extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAluno;

    // Dono do registro (escopo de usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkUsuario", nullable = false)
    private Usuario usuario;

    @ManyToMany(mappedBy = "alunos")
    private List<Responsavel> responsaveis = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkEscola", nullable = false)
    private Escola escola;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(nullable = false, length = 45)
    private String professor;

    private LocalDate dtNascimento;

    private Integer serie;

    @Column(length = 5)
    private String sala;

    // Campos para controle financeiro
    @Column(nullable = false)
    private BigDecimal valorMensalidade;

    @Column(nullable = false)
    private Integer diaVencimento; // 1-31

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensalidadeAluno> mensalidades = new ArrayList<>();

    // Relacionamento opcional com Transporte (corresponde ao mappedBy = "transporte" em Transporte)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkTransporte", nullable = true)
    private Transporte transporte;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItinerarioAluno> itinerarios = new ArrayList<>();
}