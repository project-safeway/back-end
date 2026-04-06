package com.safeway.tech.auth.core.port;

import com.safeway.tech.auth.core.model.AuthUser;

import java.util.Optional;

public interface LoadAuthUserPort {

    Optional<AuthUser> findByEmail(String email);
}
