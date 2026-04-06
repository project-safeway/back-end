package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.evento.EventoResponse;
import com.safeway.tech.domain.models.Evento;

public class EventoMapper {

    public static EventoResponse toResponse(Evento evento) {
        return new EventoResponse(
                evento.getId(),
                evento.getTitle(),
                evento.getDescription(),
                evento.getDate(),
                evento.getType(),
                evento.getPriority()
        );
    }

}
