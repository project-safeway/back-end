package com.safeway.tech.auth.infrastructure.security;

import com.safeway.tech.auth.core.port.PasswordVerifierPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptPasswordVerifierAdapter implements PasswordVerifierPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean matches(String rawPassword, String passwordHash) {
        return passwordEncoder.matches(rawPassword, passwordHash);
    }
}
