package com.safeway.tech.services;

import com.safeway.tech.dto.rotas.RotasRequest;
import com.safeway.tech.dto.rotas.RotasResponse;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RotasCompostasService {

    private List<RotasService> provedores;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public RotasResponse otimizarMelhorRota(RotasRequest request) {
        List<CompletableFuture<RotasResponse>> futures = provedores.stream()
                .map(provedor -> CompletableFuture.supplyAsync(() -> {
                    try {
                        RotasResponse response = provedor.otimizarRota(request);
                        return response;
                    } catch (Exception e) {
                        System.err.println("Provedor " + provedor.nomeProvedor() + " falhou: " + e.getMessage());
                        return null;
                    }
                }, executor))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(RotasResponse::distanciaTotal))
                .orElseThrow(() -> new RuntimeException("Nenhuma rota otimizada disponível"));
    }

    public RotasResponse otimizarComProvedor(String nomeProvedor, RotasRequest request) {
        return provedores.stream()
                .filter(provedor -> provedor.nomeProvedor().equalsIgnoreCase(nomeProvedor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Provedor não encontrado: " + nomeProvedor))
                .otimizarRota(request);
    }
}
