package com.safeway.tech.infra.exception;

public class CoordinatesNotValidException extends RuntimeException {
    public CoordinatesNotValidException(String message) {
        super(message);
    }
}
