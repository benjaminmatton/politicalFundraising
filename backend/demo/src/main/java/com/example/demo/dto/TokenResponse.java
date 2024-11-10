package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) for handling token responses.
 * This class encapsulates authentication tokens and expiration information.
 */
public class TokenResponse {
    /** The JWT access token for authentication */
    private String accessToken;
    /** The refresh token used to obtain new access tokens */
    private String refreshToken;
    /** The expiration time of the access token in milliseconds */
    private long expiresIn;

    /**
     * Constructs a new TokenResponse with the specified tokens and expiration.
     * @param accessToken The JWT access token for authentication
     * @param refreshToken The refresh token for obtaining new access tokens
     * @param expiresIn The expiration time in milliseconds
     */
    public TokenResponse(String accessToken, String refreshToken, long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    /**
     * Retrieves the access token.
     * @return The JWT access token as a String
     */
    public String getAccessToken() {
        return accessToken;
    } 

    /**
     * Retrieves the refresh token.
     * @return The refresh token as a String
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Retrieves the expiration time.
     * @return The expiration time in milliseconds
     */
    public long getExpiresIn() {
        return expiresIn;
    }
    
}