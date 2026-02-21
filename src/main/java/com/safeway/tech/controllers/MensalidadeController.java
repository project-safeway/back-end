package com.safeway.tech.controllers;

import com.safeway.tech.dto.MensalidadeResponse;
import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.services.MensalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping({"/mensalidades","/api/mensalidades"})
public class MensalidadeController {

    @Autowired
    private MensalidadeService mensalidadeService;

    @GetMapping
    public ResponseEntity<Page<MensalidadeResponse>> getMensalidades(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) List<StatusPagamento> status,
            @PageableDefault(page = 0, size = 10, sort = "dataVencimento", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        Page<MensalidadeResponse> mensalidades =
                mensalidadeService.buscarMensalidades(alunoId, dataInicio, dataFim, status, pageable);
        return ResponseEntity.ok(mensalidades);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<Page<MensalidadeResponse>> getPendentes(
            @RequestParam int mes,
            @RequestParam int ano,
            @PageableDefault(page = 0, size = 10, sort = "dataVencimento", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());
        List<StatusPagamento> status = List.of(StatusPagamento.PENDENTE, StatusPagamento.ATRASADO);
        Page<MensalidadeResponse> mensalidades = mensalidadeService.buscarMensalidades(null, dataInicio, dataFim, status, pageable);
        return ResponseEntity.ok(mensalidades);
    }

    @GetMapping("/pagas")
    public ResponseEntity<Page<MensalidadeResponse>> getPagas(
            @RequestParam int mes,
            @RequestParam int ano,
            @PageableDefault(page = 0, size = 10, sort = "dataVencimento", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());
        List<StatusPagamento> status = List.of(StatusPagamento.PAGO);
        Page<MensalidadeResponse> mensalidades = mensalidadeService.buscarMensalidades(null, dataInicio, dataFim, status, pageable);
        return ResponseEntity.ok(mensalidades);
    }

    @PatchMapping("/pagar/{id}")
    public ResponseEntity<Void> marcarComoPago(
            @PathVariable Long id
    ) {
        mensalidadeService.marcarComoPago(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/pendente/{id}")
    public ResponseEntity<Void> marcarComoPendente(@PathVariable Long id) {
        mensalidadeService.marcarComoPendente(id);
        return ResponseEntity.noContent().build();
    }
}