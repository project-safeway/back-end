package com.safeway.tech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionarios", uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(name = "uk_funcionario_usuario_cpf", columnNames = {"fkUsuario", "cpf"})
})
@Getter @Setter
@NoArgsConstructor
public class Funcionario extends BaseEntity {

    // Dono do registro (escopo de usuário)
    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_transporte", nullable = false)
    private Transporte transporte;

    @ManyToOne
    @JoinColumn(name = "fk_endereco", nullable = false)
    private Endereco endereco;

    @Column(nullable = false)
    private String nome;

    @Column
    private String cpf;
}
