package com.safeway.tech.auth.infrastructure.security;

import com.safeway.tech.auth.core.model.IssuedToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ServiceJwtTokenIssuer {

    private static final String ISSUER = "safeway-tech";

    private final JwtEncoder jwtEncoder;

    @Value("${auth.v2.service-token.expires-in-seconds:3600}")
    private long expiresInSeconds;

    public ServiceJwtTokenIssuer(@Qualifier("authV2JwtEncoder") JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public IssuedToken issueFor(String subject) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(subject)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresInSeconds))
                .claim("tokenType", "service")
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new IssuedToken(jwtValue, expiresInSeconds);
    }
}

