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
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "transportes")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Transporte {
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
    private List<AlunoTransporte> alunosTransportes;

    @OneToMany(mappedBy = "transporte")
    private List<Despesa> despesas;

    @OneToMany(mappedBy = "transporte")
    private List<Usuario> usuarios;
}
