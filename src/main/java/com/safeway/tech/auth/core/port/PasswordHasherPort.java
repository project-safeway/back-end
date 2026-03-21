package com.safeway.tech.auth.core.port;

public interface PasswordHasherPort {
    String hash(String rawPassword);
}
