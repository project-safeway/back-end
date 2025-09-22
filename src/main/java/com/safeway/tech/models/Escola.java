package com.safeway.tech.models;

import com.safeway.tech.enums.NivelEnsinoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.List;

@Entity
@Table(name = "escolas")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Escola {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEscola;

    @ManyToOne
    @JoinColumn(name = "fkEndereco", nullable = false)
    private Endereco endereco;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private NivelEnsinoEnum nivelEnsino;

    @OneToMany(mappedBy = "escola")
    private List<Aluno> alunos;
}
