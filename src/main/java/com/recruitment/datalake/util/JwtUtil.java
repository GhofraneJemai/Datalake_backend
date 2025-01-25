package com.recruitment.datalake.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.entities.Role;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = "your-secret-key"; // Store it securely, e.g. in application.properties
    private final long expirationTime = 86400000; // 1 day in milliseconds

    // Generate a JWT token
 // Generate a JWT token with user ID
    public String generateToken(String email, Role role, Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(email) // Set the subject (email)
                .withClaim("role", role.name()) // Add role as a custom claim
                .withClaim("userId", userId) // Add userId as a custom claim
                .withIssuedAt(new Date()) // Set issue date
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // Set expiration date
                .sign(algorithm); // Sign the token with the algorithm
    }


    // Extract claims from the JWT token
    public DecodedJWT extractClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey); // Use the same algorithm

        JWTVerifier verifier = JWT.require(algorithm)
                .build(); // Build the verifier with the algorithm

        return verifier.verify(token); // Verify the token and return the decoded claims
    }
    public DecodedJWT decodeJWT(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                  .build()
                  .verify(token);
    }

    // Check if the token is valid
    public boolean isTokenValid(String token, String email) {
        try {
            DecodedJWT decodedJWT = extractClaims(token);
            String tokenEmail = decodedJWT.getSubject(); // Get the subject (email) from the token
            Date expirationDate = decodedJWT.getExpiresAt(); // Get the expiration date from the token
            return email.equals(tokenEmail) && expirationDate.after(new Date()); // Check email and expiration date
        } catch (Exception e) {
            return false; // Return false if the token is invalid
        }
    }
 // Extract the userId from the token
    public Long extractUserId(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        return decodedJWT.getClaim("userId").asLong(); // Extract and return the userId
    }


    
}
