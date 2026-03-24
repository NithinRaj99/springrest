package com.springrest.springrest.security.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class PublicKeyProvider {

    private final PublicKey publicKey;

    public PublicKeyProvider(
            @Value("${jwt.rsa.public-key}") String publicKeyStr
    ) {
        try {
            byte[] decoded = Base64.getDecoder().decode(publicKeyStr);
            this.publicKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load public key", e);
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
