package com.safeway.tech.models;

import com.safeway.tech.enums.NivelEnsinoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "escolas")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
public class Escola extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEscola;

    // Dono do registro (escopo de usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkUsuario", nullable = false)
    private Usuario usuario;

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
