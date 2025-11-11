package com.safeway.tech.scheduler;

import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.MensalidadeAluno;
import com.safeway.tech.repository.AlunoRepository;
import com.safeway.tech.repository.MensalidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MensalidadeScheduler {

    private final MensalidadeRepository mensalidadeRepository;
    private final AlunoRepository alunoRepository;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void atualizarStatusMensalidades() {
        log.info("Iniciando atualização de status de mensalidades");
        LocalDate hoje = LocalDate.now();

        List<MensalidadeAluno> mensalidadesPendentes = mensalidadeRepository
                .findByStatusAndDataVencimentoBefore(StatusPagamento.PENDENTE, hoje);

        mensalidadesPendentes.forEach(mensalidade -> {
            mensalidade.setStatus(StatusPagamento.ATRASADO);
            log.debug("Mensalidade ID {} marcada como ATRASADO", mensalidade.getId());
        });

        mensalidadeRepository.saveAll(mensalidadesPendentes);
        log.info("Finalizou atualização. Total de mensalidades atrasadas: {}", mensalidadesPendentes.size());
    }

    @Scheduled(cron = "0 0 1 1 * *")
    @Transactional
    public void gerarMensalidadesMesAtual() {
        log.info("Iniciando geração de mensalidades do mês");
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        List<Aluno> alunosAtivos = alunoRepository.findByAtivoTrue();
        int mensalidadesGeradas = 0;

        for (Aluno aluno : alunosAtivos) {
            boolean jaExiste = mensalidadeRepository
                    .existsByAlunoAndDataVencimentoBetween(aluno, inicioMes, fimMes);

            if (!jaExiste) {
                LocalDate dataVencimento = calcularDataVencimento(hoje, aluno.getDiaVencimento());

                MensalidadeAluno mensalidade = new MensalidadeAluno();
                mensalidade.setAluno(aluno);
                mensalidade.setValorMensalidade(aluno.getValorMensalidade());
                mensalidade.setDataVencimento(dataVencimento);
                mensalidade.setStatus(StatusPagamento.PENDENTE);

                mensalidadeRepository.save(mensalidade);
                mensalidadesGeradas++;
            }
        }

        log.info("Finalizou geração. Total de mensalidades geradas: {}", mensalidadesGeradas);
    }

    private LocalDate calcularDataVencimento(LocalDate mesReferencia, Integer diaVencimento) {
        try {
            return mesReferencia.withDayOfMonth(diaVencimento);
        } catch (DateTimeException e) {
            log.warn("Dia {} não existe no mês {}. Usando último dia do mês.",
                    diaVencimento, mesReferencia.getMonth());
            return mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
        }
    }
}