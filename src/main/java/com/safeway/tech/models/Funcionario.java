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
@Table(name = "funcionarios")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;

    @ManyToOne
    @JoinColumn(name = "fkTransporte", nullable = false)
    private Transporte transporte;

    @ManyToOne
    @JoinColumn(name = "fkEndereco", nullable = false)
    private Endereco endereco;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "funcionario")
    private List<Pagamento> pagamentos;
}
