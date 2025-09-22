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

import java.util.List;

@Entity
@Table(name = "responsaveis")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Responsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResponsavel;

    @ManyToOne
    @JoinColumn(name = "fkEndereco", nullable = false)
    private Endereco endereco;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(nullable = false)
    private String tel1;

    private String tel2;
    private String responsavelSub;

    @OneToMany(mappedBy = "responsavel")
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "responsavel")
    private List<Usuario> usuarios;
}
