package com.safeway.tech.auth.core.exception;

public class TransporteAlreadyInUseException extends RuntimeException {

    public TransporteAlreadyInUseException() {
        super("Placa já cadastrada");
    }
}
