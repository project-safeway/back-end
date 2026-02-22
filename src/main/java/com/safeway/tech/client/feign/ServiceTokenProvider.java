package com.safeway.tech.client.feign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceTokenProvider {

    private final JwtEncoder jwtEncoder;

    private String cachedToken;
    private Instant tokenExpiration;

    public synchronized String getServiceToken() {

        if (cachedToken != null && tokenExpiration != null && Instant.now().isBefore(tokenExpiration)) {
            log.debug("Usando token de serviço em cache, expira em: {}", tokenExpiration);
            return cachedToken;
        }

        log.info("Gerando novo token de serviço para o serviço financeiro");

        Instant now = Instant.now();

        long expiresIn = 60 * 60 * 23L;
        Instant expiration = now.plusSeconds(expiresIn);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("safeway-tech")
                .subject("safeway-core")
                .issuedAt(now)
                .expiresAt(expiration)
                .build();

        cachedToken = jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
        tokenExpiration = expiration.minusSeconds(60);

        log.info("Token de serviço gerado, expira em: {}", tokenExpiration);
        return cachedToken;
    }

    public synchronized void invalidateToken() {
        cachedToken = null;
        tokenExpiration = null;
    }

}
