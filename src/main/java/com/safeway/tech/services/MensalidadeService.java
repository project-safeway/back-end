package com.safeway.tech.services;

import com.safeway.tech.dto.MensalidadeResponse;
import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.MensalidadeAluno;
import com.safeway.tech.repository.MensalidadeRepository;
import com.safeway.tech.specification.MensalidadeAlunoSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensalidadeService {

    private final MensalidadeRepository mensalidadeRepository;
    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    public Page<MensalidadeResponse> buscarMensalidades(Long alunoId, LocalDate dataInicio, LocalDate dataFim, List<StatusPagamento> status, Pageable pageable) {
        Long userId = currentUserService.getCurrentUserId();

        Specification<MensalidadeAluno> spec = Specification.allOf(
                MensalidadeAlunoSpecs.comAluno(alunoId),
                MensalidadeAlunoSpecs.comPeriodo(dataInicio, dataFim),
                MensalidadeAlunoSpecs.comStatus(status),
                MensalidadeAlunoSpecs.comUsuario(userId)
        );

        Page<MensalidadeAluno> result = mensalidadeRepository.findAll(spec, pageable);

        return result.map(MensalidadeResponse::fromEntity);
    }

    @Transactional
    public void marcarComoPago(Long mensalidadeId) {
        MensalidadeAluno mensalidade = mensalidadeRepository.findById(mensalidadeId)
                .orElseThrow(() -> new RuntimeException("Mensalidade não encontrada"));

        Long userId = currentUserService.getCurrentUserId();
        if (!mensalidade.getAluno().getUsuario().getIdUsuario().equals(userId)) {
            throw new RuntimeException("Sem permissão para alterar esta mensalidade");
        }

        mensalidade.setStatus(StatusPagamento.PAGO);
        mensalidade.setDataPagamento(LocalDate.now());

        mensalidadeRepository.save(mensalidade);
    }
}