package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) for handling payment requests.
 * This class encapsulates the information needed to process a payment.
 */
public class PaymentRequest {
    /** The amount of the payment */
    private double amount;
    
    /** The currency of the payment */
    private String currency;

    /** The candidate ID of the payment */
    private String candidateId;

    /** The user ID of the payment */
    private String userId;

    /**
     * Retrieves the amount of the payment.
     * @return The payment amount as a double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     * @param amount The payment amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the currency of the payment.
     * @return The currency as a String
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the currency of the payment.
     * @param currency The currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Retrieves the candidate ID of the payment.
     * @return The candidate ID as a String
     */
    public String getCandidateId() {
        return candidateId;
    }

    /**
     * Sets the candidate ID of the payment.
     * @param candidateId The candidate ID to set
     */
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * Retrieves the user ID of the payment.
     * @return The user ID as a String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID of the payment.
     * @param userId The user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
