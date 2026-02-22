package com.safeway.tech.controllers;

import com.safeway.tech.dto.AlunoComLocalizacao;
import com.safeway.tech.dto.ItinerarioAlunoRequest;
import com.safeway.tech.dto.ItinerarioEscolaRequest;
import com.safeway.tech.dto.ItinerarioRequest;
import com.safeway.tech.dto.ItinerarioResponse;
import com.safeway.tech.dto.ItinerarioUpdateRequest;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.services.ItinerarioAlunoService;
import com.safeway.tech.services.ItinerarioEscolaService;
import com.safeway.tech.services.ItinerarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/itinerarios")
public class ItinerarioController {

    @Autowired
    private ItinerarioService itinerarioService;

    @Autowired
    private ItinerarioAlunoService itinerarioAlunoService;

    @Autowired
    private ItinerarioEscolaService itinerarioEscolaService;

    @PostMapping
    public ResponseEntity<ItinerarioResponse> criar(
            @Valid @RequestBody ItinerarioRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID transporteUsuario = jwt.getClaim("transporte");
        ItinerarioResponse response = itinerarioService.criar(request, transporteUsuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ItinerarioResponse>> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        UUID transporte = jwt.getClaim("transporte");
        return ResponseEntity.ok(itinerarioService.listarTodos(transporte));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItinerarioResponse> buscarPorId(@PathVariable UUID id) {
        Itinerario itinerario = itinerarioService.buscarPorId(id);
        return ResponseEntity.ok(ItinerarioResponse.fromEntity(itinerario));
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<List<AlunoComLocalizacao>> buscarAlunosDoItinerario(@PathVariable UUID id) {
        List<AlunoComLocalizacao> alunos = itinerarioAlunoService.buscarAlunosComLocalizacao(id);
        return ResponseEntity.ok(alunos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItinerarioResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ItinerarioUpdateRequest request
    ) {
        return ResponseEntity.ok(itinerarioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        itinerarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/alunos")
    public ResponseEntity<Void> adicionarAluno(
            @PathVariable UUID id,
            @Valid @RequestBody ItinerarioAlunoRequest request
    ) {
        itinerarioAlunoService.adicionarAluno(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/alunos/{alunoId}")
    public ResponseEntity<Void> removerAluno(
            @PathVariable UUID id,
            @PathVariable String alunoId
    ) {
        if (alunoId == null || alunoId.isBlank() || "undefined".equalsIgnoreCase(alunoId)) {
            return ResponseEntity.badRequest().build();
        }
        UUID alunoIdLong;
        try {
            alunoIdLong = UUID.fromString(alunoId);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().build();
        }

        itinerarioAlunoService.removerAluno(id, alunoIdLong);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/alunos/ordem")
    public ResponseEntity<Void> reordenar(
            @PathVariable UUID id,
            @RequestBody List<UUID> novaOrdemIds
    ) {
        itinerarioAlunoService.reordenar(id, novaOrdemIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/escolas")
    public ResponseEntity<Void> adicionarEscola(
            @PathVariable UUID id,
            @Valid @RequestBody ItinerarioEscolaRequest request
    ) {
        itinerarioEscolaService.adicionarEscola(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/escolas/{escolaId}")
    public ResponseEntity<Void> removerEscola(
            @PathVariable UUID id,
            @PathVariable UUID escolaId
    ) {
        itinerarioEscolaService.removerEscola(id, escolaId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/escolas/ordem")
    public ResponseEntity<Void> reordenarEscolas(
            @PathVariable UUID id,
            @RequestBody List<UUID> novaOrdemEscolaIds
    ) {
        itinerarioEscolaService.reordenar(id, novaOrdemEscolaIds);
        return ResponseEntity.ok().build();
    }
}
