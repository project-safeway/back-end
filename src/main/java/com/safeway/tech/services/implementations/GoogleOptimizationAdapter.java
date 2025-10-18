package com.safeway.tech.services.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.safeway.tech.client.GoogleOptimizationClient;
import com.safeway.tech.dto.rotas.Localizacao;
import com.safeway.tech.dto.rotas.MetricasRota;
import com.safeway.tech.dto.rotas.ParadaOtimizada;
import com.safeway.tech.dto.rotas.RotasRequest;
import com.safeway.tech.dto.rotas.RotasResponse;
import com.safeway.tech.services.RotasService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("googleOptimization")
public class GoogleOptimizationAdapter implements RotasService {

    private final GoogleOptimizationClient cliente;

    public GoogleOptimizationAdapter(GoogleOptimizationClient cliente) {
        this.cliente = cliente;
    }

    @Override
    public RotasResponse otimizarRota(RotasRequest request) {
        try {
            JsonNode response = cliente.otimizarRotas(request);
            return parseResposta(response);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao otimizar rota com Google", e);
        }
    }

    private RotasResponse parseResposta(JsonNode response) {
        List<ParadaOtimizada> todasAsParadas = new ArrayList<>();
        List<MetricasRota> metricas = new ArrayList<>();
        Double distanciaTotal = 0D;
        Long tempoTotal = 0L;

        JsonNode routes = response.path("routes");
        for (JsonNode rota : routes) {
            String idVeiculo = rota.path("veiculoLabel").asText("vehicle-1");

            JsonNode visits = rota.path("visits");
            JsonNode transitions = rota.path("transitions");
            for (int i = 0; i < visits.size(); i++) {
                JsonNode visit = visits.get(i);
                JsonNode transition = i < transitions.size() ? transitions.get(i) : null;

                String idParada = visit.path("shipmentLabel").asText();
                String horarioChegada = visit.path("startTime").asText();

                Double distanciaAteAqui = 0D;
                Long tempoViagem = 0L;

                if (transition != null) {
                    distanciaAteAqui = transition.path("travelDistanceMeters").asDouble();
                    String durTravel = transition.path("travelDuration").asText();
                    tempoViagem = parseStringDuracao(durTravel);
                }

                ParadaOtimizada parada = new ParadaOtimizada(
                        idParada,
                        new Localizacao(0D,0D),
                        horarioChegada,
                        distanciaAteAqui,
                        tempoViagem
                );
                todasAsParadas.add(parada);
            }

            JsonNode metricasRota = rota.path("metrics");
            Double distanciaRota = metricasRota.path("travelDistanceMeters").asDouble();
            Long duracaoRota = parseStringDuracao(metricasRota.path("travelDuration").asText());

            MetricasRota metrica = new MetricasRota(
                    idVeiculo,
                    distanciaRota,
                    duracaoRota,
                    metricasRota.path("performedShipmentCount").asInt()
            );
            metricas.add(metrica);

            distanciaTotal += distanciaRota;
            tempoTotal += duracaoRota;
        }

        return new RotasResponse(distanciaTotal, tempoTotal, todasAsParadas, metricas, "Google");
    }

    private long parseStringDuracao(String duracao) {
        if (duracao == null || duracao.isEmpty()) return 0;
        if (duracao.endsWith("s")) {
            return Long.parseLong(duracao.substring(0, duracao.length() - 1));
        }
        return 0;
    }

    @Override
    public String nomeProvedor() {
        return "Google";
    }
}
