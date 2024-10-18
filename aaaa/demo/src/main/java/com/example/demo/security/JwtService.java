package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    // Method to validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
            .setSigningKey(getSigningKey())
            .parseClaimsJws(token);       
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to extract user ID or other claims from JWT token
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(getSigningKey())
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject(); // Assuming the user ID is stored in the subject
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}