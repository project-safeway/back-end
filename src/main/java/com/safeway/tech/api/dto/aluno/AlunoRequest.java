package com.safeway.tech.api.dto.aluno;

import com.safeway.tech.api.dto.responsavel.ResponsavelRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AlunoRequest(
        @NotBlank String nome,
        @NotBlank String professor,
        @Past LocalDate dtNascimento,
        Integer serie,
        String sala,
        @NotNull @Positive Double valorMensalidade,
        @NotNull @Min(1) @Max(31) Integer diaVencimento,
        @NotNull @NotEmpty @Valid List<ResponsavelRequest> responsaveis,
        @NotNull UUID escolaId
) {
}
