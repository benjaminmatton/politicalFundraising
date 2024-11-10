package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.access}")
    private Long accessTokenExpiration = 3600000L; // 1 hour default

    @Value("${jwt.expiration.refresh}")
    private Long refreshTokenExpiration = 2592000000L; // 30 days default

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

    public String generateAccessToken(String userId) {
        return generateToken(userId, accessTokenExpiration);
    }

    public String generateRefreshToken(String userId) {
        return generateToken(userId, refreshTokenExpiration);
    }

    private String generateToken(String userId, long expiration) {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, getSigningKey())
            .compact();
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
            .setSigningKey(getSigningKey())
            .parseClaimsJws(token)
            .getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

}