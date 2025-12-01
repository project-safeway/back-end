package com.safeway.tech.controllers;

import com.google.maps.model.LatLng;
import com.safeway.tech.dto.EnderecoRequest;
import com.safeway.tech.dto.EnderecoResponse;
import com.safeway.tech.services.EnderecoService;
import com.safeway.tech.services.GeocodingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<EnderecoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoRequest request
    ) {
        return ResponseEntity.ok(enderecoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
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