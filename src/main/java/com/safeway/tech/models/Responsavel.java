package com.safeway.tech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "responsaveis")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Responsavel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_endereco", nullable = false)
    private Endereco endereco;

    @Column(nullable = false, length = 45)
    private String nome;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column(nullable = false, length = 9)
    private String tel1;

    @Column(length = 9)
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