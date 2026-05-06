package com.sneakerhead.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT utility for generating and validating vendor tokens.
 *
 * Tokens are stored in Redis on logout (blacklist) so they can be
 * invalidated before expiry.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the given vendor email.
     *
     * @param email    vendor email (used as subject)
     * @param vendorId vendor MySQL id (stored as claim)
     */
    public String generateToken(String email, Long vendorId) {
        return Jwts.builder()
                .subject(email)
                .claim("vendorId", vendorId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extracts the email (subject) from a token.
     */
    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Extracts the vendorId claim from a token.
     */
    public Long extractVendorId(String token) {
        Object id = parseClaims(token).get("vendorId");
        if (id instanceof Integer)
            return ((Integer) id).longValue();
        return (Long) id;
    }

    /**
     * Returns true if the token is valid (signature OK and not expired).
     */
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the expiration date of the token (used to set Redis TTL).
     */
    public Date extractExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
