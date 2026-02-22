package com.safeway.tech.models;

import com.safeway.tech.enums.NivelEnsinoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "escolas")
@Getter @Setter
@NoArgsConstructor
public class Escola extends BaseEntity {

    // Dono do registro (escopo de usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco", nullable = false)
    private Endereco endereco;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private NivelEnsinoEnum nivelEnsino;

    @OneToMany(mappedBy = "escola")
    private List<Aluno> alunos;
}
