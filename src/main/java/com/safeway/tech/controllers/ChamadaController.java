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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/chamada")
@RestController
public class ChamadaController {

    @Autowired
    private ChamadaService chamadaService;

    @Autowired
    private ChamadaAlunoService chamadaAlunoService;

    @PostMapping("/iniciar/{id}")
    public ResponseEntity<ChamadaResponse> iniciarChamada(@PathVariable Long id) {
        Chamada chamada = chamadaService.iniciarChamada(id);
        return ResponseEntity.ok(ChamadaResponse.fromEntity(chamada));
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<ChamadaResponse> alterarChamada(@PathVariable Long id, @PathParam("status") StatusChamadaEnum status) {
        Chamada chamada = chamadaService.atualizarChamada(id, status);
        return ResponseEntity.ok(ChamadaResponse.fromEntity(chamada));
    }

    @PutMapping("/{id}/registrar-presenca")
    public ResponseEntity<Void> registrarPresenca(
            @PathVariable Long id,
            @RequestBody Map<Long, StatusPresencaEnum> presencas) {
        chamadaAlunoService.registrarPresenca(presencas, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/historico/{id}")
    public ResponseEntity<Page<ChamadaResponse>> historicoChamada(
            @PathVariable Long id,
            @PathParam("status") List<StatusChamadaEnum> status,
            @PageableDefault(page = 0, size = 10, sort = "dtInsert", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Chamada> chamadas = chamadaService.buscarHistoricoChamadas(id, status, pageable);
        return ResponseEntity.ok(chamadas.map(ChamadaResponse::fromEntity));
    }

}
