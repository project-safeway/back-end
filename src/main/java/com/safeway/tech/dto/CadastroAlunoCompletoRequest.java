package com.safeway.tech.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CadastroAlunoCompletoRequest(
        @NotBlank @Size(max = 45) String nome,
        @NotBlank @Size(max = 45) String professor,
        @Past LocalDate dtNascimento,
        @Min(1) Integer serie,
        @Size(max = 5) String sala,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) Double valorMensalidade,
        @NotNull @Min(1) @Max(31) Integer diaVencimento,
        @NotNull Long fkEscola,
        @Positive(message = "fkTransporte deve ser positivo") Long fkTransporte,
        @Valid List<ResponsavelComEnderecoData> responsaveis
) {
    public record ResponsavelComEnderecoData(
            @NotBlank @Size(max = 45) String nome,
            @Size(max = 14) String cpf,
            @NotBlank String tel1,
            String tel2,
            @Email String email,
            @NotNull @Valid EnderecoData endereco
    ) {}

    public record EnderecoData(
            @NotBlank String logradouro,
            @NotBlank String numero,
            String complemento,
            @NotBlank String bairro,
            @NotBlank String cidade,
            @NotBlank @Size(min = 2, max = 2) String uf,
            @NotBlank String cep,
            String tipo
    ) {}
}