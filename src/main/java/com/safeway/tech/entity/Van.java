package entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Van {
    private String placa;
    private String modelo;
    private String marca;
    private List<Estudante> estudantes;
}
