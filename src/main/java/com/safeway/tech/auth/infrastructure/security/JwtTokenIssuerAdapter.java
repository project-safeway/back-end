package com.safeway.tech.auth.infrastructure.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.safeway.tech.auth.core.model.AuthUser;
import com.safeway.tech.auth.core.model.IssuedToken;
import com.safeway.tech.auth.core.port.TokenIssuerPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class JwtTokenIssuerAdapter implements TokenIssuerPort {

    private static final String ISSUER = "safeway-tech";
    private static final long EXPIRES_IN_SECONDS = 60 * 60 * 23L;

    private final JwtEncoder jwtEncoder;

    public JwtTokenIssuerAdapter(@Qualifier("authV2RsaKeyPair") KeyPair keyPair) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        this.jwtEncoder = new NimbusJwtEncoder(jwks);
    }

    @Override
    public IssuedToken issueFor(AuthUser authUser) {
        Instant now = Instant.now();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(authUser.id().toString())
                .claim("transporte", authUser.transporteId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRES_IN_SECONDS))
                .claim("role", authUser.role());

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();

        return new IssuedToken(jwtValue, EXPIRES_IN_SECONDS);
    }
}
