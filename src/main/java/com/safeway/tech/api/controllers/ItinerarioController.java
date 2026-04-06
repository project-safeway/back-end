package com.safeway.tech.api.controllers;

import com.safeway.tech.api.dto.itinerario.AlunoComLocalizacao;
import com.safeway.tech.api.dto.itinerario.ItinerarioAlunoRequest;
import com.safeway.tech.api.dto.itinerario.ItinerarioEscolaRequest;
import com.safeway.tech.api.dto.itinerario.ItinerarioRequest;
import com.safeway.tech.api.dto.itinerario.ItinerarioResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioUpdateRequest;
import com.safeway.tech.domain.models.Itinerario;
import com.safeway.tech.service.services.ItinerarioAlunoService;
import com.safeway.tech.service.services.ItinerarioEscolaService;
import com.safeway.tech.service.services.ItinerarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ItinerarioController {

    private final ItinerarioService itinerarioService;
    private final ItinerarioAlunoService itinerarioAlunoService;
    private final ItinerarioEscolaService itinerarioEscolaService;

    @PostMapping
    public ResponseEntity<ItinerarioResponse> criar(
            @Valid @RequestBody ItinerarioRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID transporteUsuario = UUID.fromString(jwt.getClaim("transporte"));
        ItinerarioResponse response = itinerarioService.criar(request, transporteUsuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ItinerarioResponse>> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        UUID transporte = UUID.fromString(jwt.getClaim("transporte"));
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
