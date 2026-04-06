package com.safeway.tech.infra.exception;

public class ItinerarioNotFoundException extends RuntimeException {
    public ItinerarioNotFoundException(String message) {
        super(message);
    }
}
