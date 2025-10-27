package com.safeway.tech.services;

import com.safeway.tech.dto.MensalidadePendenteResponse;
import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.MensalidadeAluno;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.repository.MensalidadeRepository;
import com.safeway.tech.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MensalidadeService {

    private final MensalidadeRepository mensalidadeRepository;
    private final PagamentoRepository pagamentoRepository;

    @Transactional(readOnly = true)
    public List<MensalidadePendenteResponse> buscarMensalidadesPendentes(Integer mes, Integer ano) {
        List<StatusPagamento> statusPendentes = List.of(StatusPagamento.PENDENTE, StatusPagamento.ATRASADO);

        List<MensalidadeAluno> mensalidades = mes != null && ano != null
                ? mensalidadeRepository.findByMesAndAnoAndStatusInWithDetails(mes, ano, statusPendentes)
                : mensalidadeRepository.findByStatusInWithDetails(statusPendentes);

        return mensalidades.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void marcarComoPago(Long mensalidadeId, Long pagamentoId) {
        MensalidadeAluno mensalidade = mensalidadeRepository.findById(mensalidadeId)
                .orElseThrow(() -> new RuntimeException("Mensalidade não encontrada"));

        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        mensalidade.setStatus(StatusPagamento.PAGO);
        mensalidade.setPagamento(pagamento);
        mensalidade.setDataPagamento(LocalDate.now());

        mensalidadeRepository.save(mensalidade);
    }

    private MensalidadePendenteResponse mapToResponse(MensalidadeAluno mensalidade) {
        List<Responsavel> responsaveis = mensalidade.getAluno().getResponsaveis();
        LocalDate hoje = LocalDate.now();

        Integer diasAtraso = mensalidade.getDataVencimento().isBefore(hoje)
                ? (int) ChronoUnit.DAYS.between(mensalidade.getDataVencimento(), hoje)
                : 0;

        String mesFormatado = LocalDate.of(mensalidade.getAno(), mensalidade.getMes(), 1)
                .getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

        String nomesResp = responsaveis.stream()
                .map(Responsavel::getNome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        String telefonesResp = responsaveis.stream()
                .map(Responsavel::getTel1)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        String emailsResp = responsaveis.stream()
                .map(Responsavel::getEmail)
                .filter(email -> email != null && !email.isEmpty())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return new MensalidadePendenteResponse(
                mensalidade.getId(),
                mensalidade.getAluno().getIdAluno(),
                mensalidade.getAluno().getNome(),
                nomesResp,
                telefonesResp,
                emailsResp,
                mensalidade.getMes(),
                mensalidade.getAno(),
                mesFormatado + "/" + mensalidade.getAno(),
                mensalidade.getValorMensalidade(),
                mensalidade.getDataVencimento(),
                diasAtraso,
                mensalidade.getStatus()
        );
    }
}