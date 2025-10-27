package com.safeway.tech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CadastroAlunoCompletoRequest(
        String nome,
        String professor,
        LocalDate dtNascimento,
        Integer serie,
        String sala,
        BigDecimal valorMensalidade,
        Integer diaVencimento,
        Long fkEscola,
        Long fkTransporte,
        List<ResponsavelComEnderecoData> responsaveis
) {
    public record ResponsavelComEnderecoData(
            String nome,
            String cpf,
            String tel1,
            String tel2,
            String email,
            EnderecoData endereco
    ) {}

    public record EnderecoData(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            String cep
    ) {}
}