package space.dinhphatphat.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.secretkey}")
    private String SECRET_KEY;

    // Generate a JWT token using email as the subject
    public String generateToken(String email) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .subject(email) // Set email as subject
                .issuedAt(new Date()) // Set current date as issued time
                .expiration(new Date(System.currentTimeMillis() + 20 * 24 * 60 * 60 * 1000)) // 20 days
                .signWith(key) // Sign token with the key
                .compact(); // Create and return token
    }

    // Extract email from the JWT token
    public String extractEmail(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key) // Set the verification key
                .build()
                .parseSignedClaims(token) // Parse the token
                .getPayload(); // Extract the payload (claims)

        return claims.getSubject(); // Get the email (subject) from the claims
    }

    // Validate if the token is valid
    public boolean isTokenValid(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

            Jwts.parser()
                    .verifyWith(key) // Set the verification key
                    .build()
                    .parseSignedClaims(token); // Parse the token to check validity
            return true; // If no exception occurs, token is valid
        } catch (Exception e) {
            return false; // If exception occurs, token is invalid
        }
    }
}