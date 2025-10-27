package com.safeway.tech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "transportes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
public class Transporte extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransporte;

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

    @OneToMany(mappedBy = "transporte")
    private List<Usuario> usuarios;
}
