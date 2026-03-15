package com.safeway.tech.api.dto.chamada;

import com.safeway.tech.api.dto.aluno.AlunoResponse;
import com.safeway.tech.domain.enums.StatusPresencaEnum;

import java.time.LocalDateTime;

public record ChamadaAlunoResponse(
        AlunoResponse aluno,
        StatusPresencaEnum presenca,
        LocalDateTime dataHora
) {
}
