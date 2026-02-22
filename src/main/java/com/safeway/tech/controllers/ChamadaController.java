package com.safeway.tech.controllers;

import com.safeway.tech.dto.ChamadaResponse;
import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.enums.StatusPresencaEnum;
import com.safeway.tech.models.Chamada;
import com.safeway.tech.services.ChamadaAlunoService;
import com.safeway.tech.services.ChamadaService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/chamada")
@RestController
public class ChamadaController {

    @Autowired
    private ChamadaService chamadaService;

    @Autowired
    private ChamadaAlunoService chamadaAlunoService;

    @PostMapping("/iniciar/{id}")
    public ResponseEntity<ChamadaResponse> iniciarChamada(@PathVariable UUID id) {
        Chamada chamada = chamadaService.iniciarChamada(id);
        return ResponseEntity.ok(ChamadaResponse.fromEntity(chamada));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ChamadaResponse> alterarChamada(@PathVariable UUID id, @PathParam("status") StatusChamadaEnum status) {
        Chamada chamada = chamadaService.atualizarChamada(id, status);
        return ResponseEntity.ok(ChamadaResponse.fromEntity(chamada));
    }

    @PutMapping("/{id}/registrar-presenca")
    public ResponseEntity<Void> registrarPresenca(
            @PathVariable UUID id,
            @RequestBody Map<UUID, StatusPresencaEnum> presencas) {
        chamadaAlunoService.registrarPresenca(presencas, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/historico/{id}")
    public ResponseEntity<Page<ChamadaResponse>> historicoChamada(
            @PathVariable UUID id,
            @RequestParam(required = false) List<StatusChamadaEnum> status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        Sort.Direction direction = Sort.Direction.DESC;
        String property = "id";

        if (sort.length > 0) {
            property = sort[0];
            if (sort.length > 1) {
                direction = Sort.Direction.fromString(sort[1]);
            }
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, property));

        Page<Chamada> chamadas = chamadaService.buscarHistoricoChamadas(id, status, pageable);
        return ResponseEntity.ok(chamadas.map(ChamadaResponse::fromEntity));
    }

}
