package com.safeway.tech.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
@Getter @Setter
@NoArgsConstructor
public class Aluno extends BaseEntity {

    // Dono do registro (escopo de usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @ManyToMany(mappedBy = "alunos")
    private List<Responsavel> responsaveis = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_escola", nullable = false)
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
    private Double valorMensalidade;

    @Column(nullable = false)
    private Integer diaVencimento; // 1-31

    @Column(nullable = false)
    private Boolean ativo = true;

    // Relacionamento opcional com Transporte (corresponde ao mappedBy = "transporte" em Transporte)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transporte", nullable = true)
    private Transporte transporte;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItinerarioAluno> itinerarios = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "fk_aluno")
//    private List<Endereco> enderecos = new ArrayList<>();
}