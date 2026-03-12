package com.safeway.tech.service.services;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeocodingService {

    private final GeoApiContext context;

    public LatLng obterCoordenadas(String endereco) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, endereco).await();

            if (results != null && results.length > 0) {
                return results[0].geometry.location;
            }

            throw new RuntimeException("Endereço não encontrado");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar coordenadas: " + e.getMessage(), e);
        }
    }

    public String obterEnderecoFormatado(String endereco) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, endereco).await();

            if (results != null && results.length > 0) {
                return results[0].formattedAddress;
            }

            throw new RuntimeException("Endereço não encontrado");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço: " + e.getMessage(), e);
        }
    }
}
