package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) for handling error responses.
 * Used to provide error information to clients in a consistent format.
 */
public class ErrorResponse {
    /** The error message describing what went wrong */
    private String message;

    /**
     * Constructs a new ErrorResponse with the specified message.
     * @param message The error message to include
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Gets the error message.
     * @return The error message as a String
     */
    public String getMessage() {
        return message;
    }
} 