package com.safeway.tech.infra.exception;

public class EnderecoNotFoundException extends RuntimeException {
    public EnderecoNotFoundException(String message) {
        super(message);
    }
}
