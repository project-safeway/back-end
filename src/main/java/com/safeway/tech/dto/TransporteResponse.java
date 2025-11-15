package com.safeway.tech.dto;

public record TransporteResponse(
        String placa,
        String modelo,
        Integer capacidade
) {

    public static TransporteResponse fromEntity(com.safeway.tech.models.Transporte transporte) {
        return new TransporteResponse(
                transporte.getPlaca(),
                transporte.getModelo(),
                transporte.getCapacidade()
        );
    }
}
