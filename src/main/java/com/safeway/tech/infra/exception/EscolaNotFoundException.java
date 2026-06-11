package com.safeway.tech.infra.exception;

public class EscolaNotFoundException extends RuntimeException {
    public EscolaNotFoundException(String message) {
        super(message);
    }
}
