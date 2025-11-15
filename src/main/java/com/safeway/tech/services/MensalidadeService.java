package com.safeway.tech.services;

import com.safeway.tech.dto.MensalidadeResponse;
import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.MensalidadeAluno;
import com.safeway.tech.repository.MensalidadeRepository;
import lombok.RequiredArgsConstructor;
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
    public List<MensalidadeResponse> buscarMensalidades(Integer mes, Integer ano, List<StatusPagamento> status) {
        Long userId = currentUserService.getCurrentUserId();

        List<MensalidadeAluno> mensalidades = new ArrayList<>();
        if (mes != null && ano != null) {
            LocalDate dataInicio = LocalDate.of(ano, mes, 1);
            LocalDate dataFinal = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

            mensalidades = mensalidadeRepository
                    .findByMesAndAnoAndStatusInWithDetails(dataInicio, dataFinal, status, userId);
        } else if (mes != null) {
            int anoAtual = LocalDate.now().getYear();
            LocalDate dataInicio = LocalDate.of(anoAtual, mes, 1);
            LocalDate dataFinal = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

            mensalidades = mensalidadeRepository
                    .findByMesAndAnoAndStatusInWithDetails(dataInicio, dataFinal, status, userId);
        } else if (ano != null) {
            LocalDate dataInicio = LocalDate.of(ano, 1, 1);
            LocalDate dataFinal = LocalDate.of(ano, 12, 31);

            mensalidades = mensalidadeRepository
                    .findByMesAndAnoAndStatusInWithDetails(dataInicio, dataFinal, status, userId);
        } else if (status != null && !status.isEmpty()) {
            mensalidades = mensalidadeRepository
                    .findByStatusInWithDetails(status, userId);
        } else {
            mensalidades = mensalidadeRepository
                    .findByUserId(userId);
        }
        return mensalidades.stream()
                .map(this::mapToResponse)
                .toList();
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

    private MensalidadeResponse mapToResponse(MensalidadeAluno mensalidade) {
        return new MensalidadeResponse(
                mensalidade.getId(),
                mensalidade.getAluno().getNome(),
                mensalidade.getAluno().getResponsaveis().getFirst().getNome(),
                mensalidade.getDataVencimento(),
                mensalidade.getDataPagamento(),
                mensalidade.getValorMensalidade(),
                mensalidade.getValorPago(),
                mensalidade.getStatus()
        );
    }
}