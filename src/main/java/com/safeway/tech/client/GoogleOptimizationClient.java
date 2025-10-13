package com.safeway.tech.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.auth.oauth2.GoogleCredentials;
import com.safeway.tech.dto.rotas.Coordenada;
import com.safeway.tech.dto.rotas.RotasRequest;
import com.safeway.tech.dto.rotas.Veiculo;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class GoogleOptimizationClient {

    private final WebClient cliente;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String projetoId;
    private final GoogleCredentials credenciais;

    public GoogleOptimizationClient(WebClient.Builder builder, String projetoId) {
        this.cliente = builder
                .baseUrl("https://routeoptimization.googleapis.com")
                .build();
        this.projetoId = projetoId;

        try {
            this.credenciais = inicializarCredenciais();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao inicializar as credenciais do Google. " +
                    "Certifique-se de que GOOGLE_APPLICATION_CREDENTIALS está definido para o caminho do arquivo JSON da conta de serviço.", e);
        }
    }

    private GoogleCredentials inicializarCredenciais() throws IOException {
        String caminhoCredenciais = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (caminhoCredenciais != null && !caminhoCredenciais.isEmpty()) {
            System.out.println("Usando conta de serviço de: " + caminhoCredenciais);
            try (FileInputStream contaDeServico = new FileInputStream(caminhoCredenciais)) {
                return GoogleCredentials.fromStream(contaDeServico)
                        .createScoped("https://www.googleapis.com/auth/cloud-platform");
            }
        }

        return GoogleCredentials.getApplicationDefault()
                .createScoped("https://www.googleapis.com/auth/cloud-platform");
    }

    public JsonNode otimizarRotas(RotasRequest request) {
        try {
            ObjectNode body = criarOtimizacaoRequest(request);

            return cliente.post()
                    .uri("/v1/projects/{projetoId}:optimizeTours", projetoId)
                    .header("Authorization", "Bearer " + getTokenAcesso())
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block(Duration.ofSeconds(30));
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Erro na chamada à API de Otimização de Rotas do Google: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao otimizar rotas: " + e.getMessage(), e);
        }
    }

    private ObjectNode criarOtimizacaoRequest(RotasRequest request) {
        ObjectNode root = mapper.createObjectNode();
        ObjectNode model = mapper.createObjectNode();

        ArrayNode shipments = mapper.createArrayNode();
        for (PontosParada ponto : request.pontosParada()) {
            ObjectNode shipment = mapper.createObjectNode();

            ArrayNode deliveries = mapper.createArrayNode();
            ObjectNode delivery = mapper.createObjectNode();
            delivery.set("arrivalLocation", criarNodeLocalizacao(ponto.localizacao()));
            delivery.put("duration", ponto.duracaoParada());

            if (ponto.pesoKg() > 0) {
                ObjectNode pesagem = mapper.createObjectNode();
                pesagem.put("amount", ponto.pesoKg());
                delivery.put("loadDemands", mapper.createObjectNode().set("weight", pesagem));
            }

            deliveries.add(delivery);
            shipment.set("deliveries", deliveries);
            shipment.put("label", ponto.id());

            shipments.add(shipment);
        }
        model.set("shipments", shipments);

        ArrayNode vehicles = mapper.createArrayNode();
        for (Veiculo veiculo : request.veiculo()) {
            ObjectNode vehicleNode = mapper.createObjectNode();
            vehicleNode.put("label", veiculo.id());
            vehicleNode.set("startLocation", criarNodeLocalizacao(veiculo.localizacaoInicial()));
            vehicleNode.set("endLocation", criarNodeLocalizacao(veiculo.localizacaoFinal()));

            if (veiculo.capacidadeKg() > 0) {
                ObjectNode capacidade = mapper.createObjectNode();
                capacidade.put("maxLoad", veiculo.capacidadeKg());
                vehicleNode.set("loadLimits", mapper.createObjectNode().set("weight", capacidade));
            }

            vehicles.add(vehicleNode);
        }
        model.set("vehicles", vehicles);

        if (request.horarioInicio() != null) {
            model.put("globalStartTime", request.horarioInicio());
        }
        if (request.horarioFim() != null) {
            model.put("globalEndTime", request.horarioFim());
        }

        root.set("model", model);
        return root;
    }

    private ObjectNode criarNodeLocalizacao(Coordenada coordenada) {
        ObjectNode location = mapper.createObjectNode();
        location.put("latitude", coordenada.lat());
        location.put("longitude", coordenada.lng());
        return location;
    }

    private String getTokenAcesso() {
        try {
            credenciais.refreshIfExpired();
            return credenciais.getAccessToken().getTokenValue();
        } catch (IOException e) {
            System.err.println("Erro ao obter o token de acesso: " + e.getMessage());

            String envToken = System.getenv("GOOGLE_ACCESS_TOKEN");
            if (envToken != null && !envToken.isEmpty()) {
                System.out.println("Usando token de acesso do ambiente.");
                return envToken;
            } else {
                throw new RuntimeException("Falha ao obter o token de acesso e GOOGLE_ACCESS_TOKEN não está definido.", e);
            }
        }
    }
}
