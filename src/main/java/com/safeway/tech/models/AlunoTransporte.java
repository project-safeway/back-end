package com.safeway.tech.models;

import com.safeway.tech.enums.TipoViagemEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "alunos_transportes")
@Data
@NoArgsConstructor @AllArgsConstructor
public class AlunoTransporte extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlunoTransporte;

    @ManyToOne
    @JoinColumn(name = "fkAluno", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "fkTransporte", nullable = false)
    private Transporte transporte;

    @Enumerated(EnumType.STRING)
    private TipoViagemEnum tipoViagem;

    private Date dataInicio;
    private Date dataFim;

    @OneToMany(mappedBy = "alunoTransporte")
    private List<Mensalidade> mensalidades;
}

