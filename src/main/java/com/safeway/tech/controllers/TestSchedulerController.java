package com.safeway.tech.controllers;

import com.safeway.tech.scheduler.MensalidadeScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestSchedulerController {

    private final MensalidadeScheduler mensalidadeScheduler;

    @PostMapping("/gerar-mensalidades")
    public ResponseEntity<String> gerarMensalidades() {
        mensalidadeScheduler.gerarMensalidadesMesAtual();
        return ResponseEntity.ok("Mensalidades geradas com sucesso!");
    }

    @PostMapping("/atualizar-status")
    public ResponseEntity<String> atualizarStatus() {
        mensalidadeScheduler.atualizarStatusMensalidades();
        return ResponseEntity.ok("Status atualizado com sucesso!");
    }
}