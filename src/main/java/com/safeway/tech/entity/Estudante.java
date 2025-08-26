package entity;

import com.safeway.tech.enums.caminhoEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Estudante {
    private String nome;
    private String dtNasc;
    private String escola;
    private String serie;
    private String turma;
    private String professora;
    private caminhoEnum caminho;
    private List<Responsavel> responsaveis = new ArrayList<>();
}
