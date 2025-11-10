package com.safeway.tech.controllers;

import com.safeway.tech.dto.ItinerarioAlunoRequest;
import com.safeway.tech.dto.ItinerarioRequest;
import com.safeway.tech.dto.ItinerarioResponse;
import com.safeway.tech.dto.ItinerarioUpdateRequest;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.services.ItinerarioAlunoService;
import com.safeway.tech.services.ItinerarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/itinerarios")
public class ItinerarioController {

    @Autowired
    private ItinerarioService itinerarioService;

    @Autowired
    private ItinerarioAlunoService itinerarioAlunoService;

    @PostMapping
    public ResponseEntity<ItinerarioResponse> criar(@Valid @RequestBody ItinerarioRequest request) {
        ItinerarioResponse response = itinerarioService.criar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ItinerarioResponse>> listarTodos() {
        return ResponseEntity.ok(itinerarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItinerarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itinerarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItinerarioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItinerarioUpdateRequest request
    ) {
        return ResponseEntity.ok(itinerarioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        itinerarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/alunos")
    public ResponseEntity<Void> adicionarAluno(
            @PathVariable Long id,
            @Valid @RequestBody ItinerarioAlunoRequest request
    ) {
        itinerarioAlunoService.adicionarAluno(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/alunos/{alunoId}")
    public ResponseEntity<Void> removerAluno(
            @PathVariable Long id,
            @PathVariable Long alunoId
    ) {
        itinerarioAlunoService.removerAluno(id, alunoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/alunos/ordem")
    public ResponseEntity<Void> reordenar(
            @PathVariable Long id,
            @RequestBody List<Long> novaOrdemIds
    ) {
        itinerarioAlunoService.reordenar(id, novaOrdemIds);
        return ResponseEntity.ok().build();
    }

}
