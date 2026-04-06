package com.safeway.tech.infra.exception;

public class EventoNotFoundException extends RuntimeException {
    public EventoNotFoundException(String message) {
        super(message);
    }
}
