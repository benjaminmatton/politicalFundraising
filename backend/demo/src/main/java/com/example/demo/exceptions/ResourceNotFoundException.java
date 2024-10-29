package com.example.demo.exceptions;

/**
 * Custom exception class for handling cases where a requested resource is not found.
 * This exception extends RuntimeException, making it an unchecked exception.
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     * 
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}