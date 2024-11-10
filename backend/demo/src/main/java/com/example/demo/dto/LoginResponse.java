package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) for handling login responses.
 * Used to provide authentication token to clients after successful login.
 */
public class LoginResponse {
    /** The JWT authentication token */
    private String token;

    /**
     * Constructs a new LoginResponse with the specified token.
     * @param token The JWT authentication token to include
     */
    public LoginResponse(String token) {
        this.token = token;
    }

    /**
     * Gets the authentication token.
     * @return The JWT token as a String
     */
    public String getToken() {
        return token;
    }
}
