package com.safeway.tech.infra.exception;

public class EnderecoAlreadyExistsException extends RuntimeException {
    public EnderecoAlreadyExistsException(String message) {
        super(message);
    }
}
