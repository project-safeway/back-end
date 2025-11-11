package com.safeway.tech.controllers;

import com.safeway.tech.dto.MensalidadeResponse;
import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.services.MensalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensalidades")
public class MensalidadeController {

    @Autowired
    private MensalidadeService mensalidadeService;

    @GetMapping("/pendentes")
    public ResponseEntity<List<MensalidadeResponse>> getMensalidades(
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) List<StatusPagamento> status
            ) {
        List<MensalidadeResponse> mensalidades =
                mensalidadeService.buscarMensalidadesPendentes(mes, ano, status);
        return ResponseEntity.ok(mensalidades);
    }

    @PatchMapping("/pagar/{id}")
    public ResponseEntity<Void> marcarComoPago(
            @PathVariable Long id
    ) {
        mensalidadeService.marcarComoPago(id);
        return ResponseEntity.noContent().build();
    }
}