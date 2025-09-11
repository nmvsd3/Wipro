package com.playstore.userservice.security; 

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

// Utility class for generating, validating, and parsing JWT tokens
@Component
public class JwtUtil {

    // Secret key for signing JWTs (should be stored securely in env/config in production)
    private static final String SECRET = "Replace_with_a_very_long_secret_key_for_production_@2025";

    // Token expiration time (1 hour in milliseconds)
    private static final long EXPIRATION_MS = 1000L * 60 * 60;

    // Cryptographic signing key
    private final Key key;

    // Initialize signing key from the secret
    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT with subject, role, and optional claims
    public String generateToken(String subject, String role, Map<String, Object> additionalClaims) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject) // the user identifier (e.g., email)
                .setIssuedAt(new Date()) // issued timestamp
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // expiration timestamp
                .signWith(key, SignatureAlgorithm.HS256) // sign with key and algorithm
                .claim("role", role); // include user role

        // Add extra claims if provided
        if (additionalClaims != null && !additionalClaims.isEmpty()) {
            builder.addClaims(additionalClaims);
        }

        return builder.compact(); // return token as string
    }

    // Overloaded method to generate token without additional claims
    public String generateToken(String subject, String role) {
        return generateToken(subject, role, null);
    }

    // Validate token structure and signature
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
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
