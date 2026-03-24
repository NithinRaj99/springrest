package com.springrest.springrest.security.jwt;

import com.springrest.springrest.entity.enums.Role;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Service;

import java.util.Date;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

@Service
public class JwtService {

    private final PublicKeyProvider publicKeyProvider;

    public JwtService(PublicKeyProvider publicKeyProvider) {
        this.publicKeyProvider = publicKeyProvider;
    }

    // 1. Extract Username (Subject)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. Extract Specific Claim (e.g., Role)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 3. Validate Token (Check signature & expiration)
    public boolean validateToken(String token) {
        try {
            // If parsing succeeds, the signature is valid
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // KEY CHANGE: Use Public Key for parsing
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKeyProvider.getPublicKey()) // <--- Uses RSA Public Key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 1. Get User ID (Handles the Integer/Long conversion safely)
    public Long extractUserId(String token) {
        // JWT libraries often parse numbers as Integer by default.
        // We get it as a Generic Number first, then convert to Long.
        Number userId = extractAllClaims(token).get("userId", Number.class);
        return (userId != null) ? userId.longValue() : null;
    }

    // 2. Get Email (This was stored in the 'Subject')
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUserName(String token) {
        return extractAllClaims(token).get("userName", String.class);
    }

    // 3. Get Role (Stored as a String)
    public Role extractRole(String token) {
        return Role.valueOf(extractAllClaims(token).get("role", String.class));
    }
}