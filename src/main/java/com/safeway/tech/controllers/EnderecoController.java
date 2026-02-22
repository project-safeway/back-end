package com.safeway.tech.controllers;

import com.safeway.tech.dto.EnderecoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.services.EnderecoService;
import com.safeway.tech.services.GeocodingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private GeocodingService geocodingService;

    @PostMapping
    public ResponseEntity<EnderecoResponse> criar(@Valid @RequestBody EnderecoRequest request) {
        return ResponseEntity.ok(enderecoService.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody EnderecoRequest request
    ) {
        return ResponseEntity.ok(enderecoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        enderecoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para testar o GeocodingService diretamente

//    @GetMapping("/coordenadas")
//    public ResponseEntity<Map<String, Object>> obterCoordenadas(@RequestParam String endereco) {
//        try {
//            LatLng coordenadas = geocodingService.obterCoordenadas(endereco);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("endereco", endereco);
//            response.put("latitude", coordenadas.lat);
//            response.put("longitude", coordenadas.lng);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            Map<String, Object> error = new HashMap<>();
//            error.put("erro", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
//
//    @GetMapping("/endereco-formatado")
//    public ResponseEntity<Map<String, String>> obterEnderecoFormatado(@RequestParam String endereco) {
//        try {
//            String enderecoFormatado = geocodingService.obterEnderecoFormatado(endereco);
//
//            Map<String, String> response = new HashMap<>();
//            response.put("original", endereco);
//            response.put("formatado", enderecoFormatado);
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("erro", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
}