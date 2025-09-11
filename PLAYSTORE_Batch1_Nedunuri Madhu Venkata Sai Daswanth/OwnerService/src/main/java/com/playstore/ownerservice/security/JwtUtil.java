package com.playstore.ownerservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

// Utility class for handling JWT operations (generation, validation, parsing)
@Component
public class JwtUtil {

    // Secret key for signing tokens (should be stored securely in env/config in production)
    private static final String SECRET = "Replace_with_a_very_long_secret_key_for_production_@2025";

    // Token expiration time (1 hour in milliseconds)
    private static final long EXPIRATION_MS = 1000L * 60 * 60;

    // Cryptographic key used for signing and validating tokens
    private final Key key;

    // Initialize the key using the secret
    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT with subject, role, and optional extra claims
    public String generateToken(String subject, String role, Map<String, Object> additionalClaims) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject) // user/email/identifier
                .setIssuedAt(new Date()) // issue time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // expiry time
                .signWith(key, SignatureAlgorithm.HS256) // sign with secret key
                .claim("role", role); // add role claim

        // Add any extra claims if provided
        if (additionalClaims != null && !additionalClaims.isEmpty()) {
            builder.addClaims(additionalClaims);
        }

        return builder.compact(); // finalize and return token
    }

    // Overloaded method for token generation without extra claims
    public String generateToken(String subject, String role) {
        return generateToken(subject, role, null);
    }

    // Validate token structure and signature
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // valid token
        } catch (JwtException | IllegalArgumentException ex) {
            return false; // invalid token
        }
    }

    // Extract username (subject) from token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // Extract role from token
    public String getRole(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        Object r = claims.get("role");
        return r == null ? null : r.toString();
    }
}
