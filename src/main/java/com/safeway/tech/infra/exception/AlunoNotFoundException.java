package com.safeway.tech.infra.exception;

public class AlunoNotFoundException extends RuntimeException {
    public AlunoNotFoundException(String message) {
        super(message);
    }
}
