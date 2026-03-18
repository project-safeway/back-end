package com.safeway.tech.auth.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.converter.RsaKeyConverters;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class AuthJwtKeyConfig {

    private static final Logger log = LoggerFactory.getLogger(AuthJwtKeyConfig.class);

    @Value("${auth.v2.jwt.public.key:}")
    private String publicKeyLocation;

    @Value("${auth.v2.jwt.private.key:}")
    private String privateKeyLocation;

    @Bean("authV2RsaKeyPair")
    public KeyPair authV2RsaKeyPair() {
        try {
            if (publicKeyLocation != null && !publicKeyLocation.isBlank()
                    && privateKeyLocation != null && !privateKeyLocation.isBlank()) {
                RSAPublicKey publicKey = RsaKeyConverters.x509().convert(loadInputStream(publicKeyLocation));
                RSAPrivateKey privateKey = RsaKeyConverters.pkcs8().convert(loadInputStream(privateKeyLocation));
                return new KeyPair(publicKey, privateKey);
            }
        } catch (Exception e) {
            log.warn("Falha ao carregar chaves RSA do Auth V2 ({} / {}). Gerando chaves em memória. Detalhe: {}",
                    publicKeyLocation, privateKeyLocation, e.getMessage());
        }

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Falha ao gerar par de chaves RSA do Auth V2", e);
        }
    }

    private InputStream loadInputStream(String location) throws IOException {
        if (location.startsWith("classpath:")) {
            String path = location.substring("classpath:".length());
            return new ClassPathResource(path).getInputStream();
        }
        return new FileInputStream(location);
    }
}
