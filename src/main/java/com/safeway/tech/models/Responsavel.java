package com.safeway.tech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "responsaveis",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_responsavel_usuario_cpf", columnNames = {"fkUsuario", "cpf"})
        })
@Getter @Setter
@NoArgsConstructor
public class Responsavel extends BaseEntity {

    // Dono do registro (escopo de usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_endereco", nullable = false)
    private Endereco endereco;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(length = 14)
    private String cpf;

    @Column(nullable = false, length = 15)
    private String tel1;

    @Column(length = 15)
    private String tel2;

    private String email;

    @ManyToMany
    @JoinTable(
            name = "responsavel_aluno",
            joinColumns = @JoinColumn(name = "responsavel_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos = new ArrayList<>();
}