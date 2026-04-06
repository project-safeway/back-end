package com.safeway.tech.auth.core.port;

public interface PasswordVerifierPort {

    boolean matches(String rawPassword, String passwordHash);
}

