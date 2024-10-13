package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.stripe.exception.StripeException;

/**
 * Global exception handler for the application.
 * This class provides centralized exception handling across all @RequestMapping methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException by returning a 404 NOT_FOUND status.
     * 
     * @param ex The ResourceNotFoundException that was thrown
     * @return A ResponseEntity with NOT_FOUND status and the exception message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles StripeException by returning a 400 BAD_REQUEST status.
     * This is typically used for payment processing errors.
     * 
     * @param ex The StripeException that was thrown
     * @return A ResponseEntity with BAD_REQUEST status and a formatted error message
     */
    @ExceptionHandler(StripeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleStripeException(StripeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Payment processing error: " + ex.getMessage());
    }

    /**
     * Handles all other exceptions by returning a 500 INTERNAL_SERVER_ERROR status.
     * This is a catch-all handler for unexpected errors.
     * 
     * @param ex The Exception that was thrown
     * @return A ResponseEntity with INTERNAL_SERVER_ERROR status and a generic error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error occurred: " + ex.getMessage());
    }
}
