package com.safeway.tech.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AlunoUpdateRequest(
        @NotBlank @Size(max = 45) String nome,
        @NotBlank @Size(max = 45) String professor,
        @Past LocalDate dtNascimento,
        @Min(1) Integer serie,
        @Size(max = 5) String sala,
        @NotNull @DecimalMin(value = "0.0") Double valorMensalidade,
        @NotNull @Min(1) @Max(31) Integer diaVencimento,
        @NotNull UUID fkEscola,
        @Positive(message = "fkTransporte deve ser positivo") UUID fkTransporte,
        @Valid List<ResponsavelUpdateData> responsaveis
) {
    public record ResponsavelUpdateData(
            UUID idResponsavel, // null = novo, preenchido = atualizar
            boolean deletar,    // true = remover esse responsavel do aluno (e opcionalmente deletar)

            @NotBlank @Size(max = 45) String nome,
            @Size(max = 14) String cpf,
            @NotBlank String tel1,
            String tel2,
            @Email String email,
            @NotNull @Valid EnderecoUpdateData endereco
    ) {}

    public record EnderecoUpdateData(
            UUID idEndereco, // para futuros usos, se houver mais de um endereco
            @NotBlank String logradouro,
            @NotBlank String numero,
            String complemento,
            @NotBlank String bairro,
            @NotBlank String cidade,
            @NotBlank @Size(min = 2, max = 2) String uf,
            @NotBlank String cep,
            @NotNull Double latitude,
            @NotNull Double longitude,
            String tipo
    ) {}
}
