package com.safeway.tech.infra.exception;

public class ItinerarioEscolaNotFound extends RuntimeException {
    public ItinerarioEscolaNotFound(String message) {
        super(message);
    }
}
