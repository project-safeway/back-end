package com.safeway.tech.infra.exception;

public class TransporteNotFoundException extends RuntimeException {
    public TransporteNotFoundException(String message) {
        super(message);
    }
}
