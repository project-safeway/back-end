package com.safeway.tech.messaging.publishers;

import com.safeway.tech.messaging.config.RabbitMQProperties;
import com.safeway.tech.messaging.event.AlunoEvent;
import com.safeway.tech.models.Aluno;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    public void publicarAlunoCriado(Aluno aluno) {
        log.info("Publicando evento de aluno criado: {}", aluno.getId());

        try {
            AlunoEvent event = new AlunoEvent(
                    UUID.randomUUID(),
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getValorMensalidade(),
                    aluno.getDiaVencimento(),
                    aluno.getAtivo(),
                    "ALUNO_CRIADO",
                    LocalDateTime.now()
            );

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getExchanges().getAluno(),
                    rabbitMQProperties.getRoutingKeys().getAlunoCriado(),
                    event
            );

            log.info("Evento de aluno criado publicado com sucesso: {}", event.alunoId());
        } catch(Exception e) {
            log.error("Erro ao publicar evento de aluno criado", e);
        }
    }

    public void publicarAlunoAtualizado(Aluno aluno) {
        log.info("Publicando evento de aluno atualizado: {}", aluno.getId());

        try {
            AlunoEvent event = new AlunoEvent(
                    UUID.randomUUID(),
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getValorMensalidade(),
                    aluno.getDiaVencimento(),
                    aluno.getAtivo(),
                    "ALUNO_ATUALIZADO",
                    LocalDateTime.now()
            );

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getExchanges().getAluno(),
                    rabbitMQProperties.getRoutingKeys().getAlunoAtualizado(),
                    event
            );

            log.info("Evento de aluno atualizado publicado com sucesso: {}", event.alunoId());
        } catch(Exception e) {
            log.error("Erro ao publicar evento de aluno atualizado", e);
        }
    }

    public void publicarAlunoInativado(Aluno aluno) {
        log.info("Publicando evento de aluno inativado: {}", aluno.getId());

        try {
            AlunoEvent event = new AlunoEvent(
                    UUID.randomUUID(),
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getValorMensalidade(),
                    aluno.getDiaVencimento(),
                    aluno.getAtivo(),
                    "ALUNO_INATIVADO",
                    LocalDateTime.now()
            );

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getExchanges().getAluno(),
                    rabbitMQProperties.getRoutingKeys().getAlunoInativado(),
                    event
            );

            log.info("Evento de aluno inativado publicado com sucesso: {}", event.alunoId());
        } catch(Exception e) {
            log.error("Erro ao publicar evento de aluno inativado", e);
        }
    }

}
