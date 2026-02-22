package com.safeway.tech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "transportes")
@Getter @Setter
@NoArgsConstructor
public class Transporte extends BaseEntity {

    @Column(nullable = false, length = 7)
    private String placa;

    private String modelo;
    private Integer capacidade;

    @OneToMany(mappedBy = "transporte")
    private List<Funcionario> funcionarios;

    @OneToMany(mappedBy = "transporte")
    private List<Aluno> alunosTransportes;

    @OneToMany(mappedBy = "transporte")
    private List<Despesa> despesas;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkUsuario", nullable = false)
    private Usuario usuario;
}
