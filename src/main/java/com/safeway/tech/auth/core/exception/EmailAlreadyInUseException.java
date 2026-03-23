package com.safeway.tech.auth.core.exception;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException() {
        super("Email já cadastrado");
    }
}
