package com.safeway.tech.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String sub = jwt.getSubject();
            return Long.valueOf(sub);
        }
        // Fallback: em alguns providers, getName() é o subject
        String name = auth.getName();
        return Long.valueOf(name);
    }

    public Long getCurrentTransporteId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof Jwt jwt) {
            Object transporteClaim = jwt.getClaim("transporte");

            if (transporteClaim == null) {
                throw new IllegalStateException("Claim 'transporte' não encontrada no token");
            }

            return Long.valueOf(transporteClaim.toString());
        }

        throw new IllegalStateException("Token JWT não encontrado no authentication principal");
    }
}

