package com.safeway.tech.domain.models;

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

    @ManyToMany(mappedBy = "alunos", fetch = FetchType.EAGER)
    private List<Responsavel> responsaveis = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_escola", nullable = false)
    private Escola escola;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transporte")
    private Transporte transporte;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(nullable = false, length = 45)
    private String professor;

    private LocalDate dtNascimento;

    private Integer serie;

    @Column(length = 5)
    private String sala;

    @Column(nullable = false)
    private Double valorMensalidade;

    @Column(nullable = false)
    private Integer diaVencimento;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItinerarioAluno> itinerarios = new ArrayList<>();

    public void adicionarResponsavel(Responsavel responsavel) {
        if (!this.responsaveis.contains(responsavel)) {
            this.responsaveis.add(responsavel);
            responsavel.getAlunos().add(this);
        }
    }

    public void removerResponsavel(Responsavel responsavel) {
        if (this.responsaveis.remove(responsavel)) {
            responsavel.getAlunos().remove(this);
        }
    }
}
