package com.safeway.tech.controllers;

import com.safeway.tech.dto.MensalidadePendenteResponse;
import com.safeway.tech.services.MensalidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensalidades")
@RequiredArgsConstructor
public class MensalidadeController {

    private final MensalidadeService mensalidadeService;

    @GetMapping("/pendentes")
    public ResponseEntity<List<MensalidadePendenteResponse>> getMensalidadesPendentes(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano
    ) {
        List<MensalidadePendenteResponse> mensalidades =
                mensalidadeService.buscarMensalidadesPendentes(mes, ano);
        return ResponseEntity.ok(mensalidades);
    }

    @PutMapping("/{id}/marcar-pago")
    public ResponseEntity<Void> marcarComoPago(
            @PathVariable Long id,
            @RequestParam Long pagamentoId
    ) {
        mensalidadeService.marcarComoPago(id, pagamentoId);
        return ResponseEntity.noContent().build();
    }
}