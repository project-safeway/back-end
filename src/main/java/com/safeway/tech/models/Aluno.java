package com.safeway.tech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "alunos")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAluno;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel", nullable = false)
    private Responsavel responsavel;

    @ManyToOne
    @JoinColumn(name = "fkEscola", nullable = false)
    private Escola escola;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String professor;

    private Date dtNascimento;
    private Integer serie;
    private String sala;

    @OneToMany(mappedBy = "aluno")
    private List<AlunoTransporte> alunosTransportes;
}
