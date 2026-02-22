package com.safeway.tech.specification;

import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.models.Chamada;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class ChamadaSpecs {

    public static Specification<Chamada> comItinerarioId(UUID itinerarioId) {
        return (root, query, cb) ->
                itinerarioId == null ? null : cb.equal(root.get("itinerario").get("id"), itinerarioId);
    }

    public static Specification<Chamada> comStatus(List<StatusChamadaEnum> statusList) {
        return (root, query, cb) ->
                statusList == null || statusList.isEmpty() ? null : root.get("status").in(statusList);
    }

    public static Specification<Chamada> comTransporte(UUID transporteId) {
        return (root, query, cb) ->
                transporteId == null ? null : cb.equal(root.get("itinerario").get("transporte").get("id"), transporteId);
    }

    public static Specification<Chamada> comUsuario(UUID usuarioId) {
        return (root, query, cb) ->
                usuarioId == null ? null : cb.equal(root.get("itinerario").get("transporte").get("usuario").get("idUsuario"), usuarioId);
    }
}
