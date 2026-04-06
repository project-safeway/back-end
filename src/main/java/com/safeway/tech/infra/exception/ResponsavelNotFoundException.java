package com.safeway.tech.infra.exception;

public class ResponsavelNotFoundException extends RuntimeException {
    public ResponsavelNotFoundException(String message) {
        super(message);
    }
}
