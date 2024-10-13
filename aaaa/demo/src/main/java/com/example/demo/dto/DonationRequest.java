package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) for handling donation requests.
 * This class encapsulates the information needed to process a donation.
 */
public class DonationRequest {
    /** The unique identifier of the user making the donation */
    //@NotNull --> could implement with import jakarta.validation.constraints.NotNull;

    private String userId;
    
    /** The unique identifier of the candidate receiving the donation */
    private String candidateId;
    
    /** The amount of the donation */
    private double amount;

    /**
     * Retrieves the user ID associated with this donation request.
     * @return The user ID as a String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Retrieves the candidate ID associated with this donation request.
     * @return The candidate ID as a String
     */
    public String getCandidateId() {  
        return candidateId;
    }

    /**
     * Retrieves the amount of the donation.
     * @return The donation amount as a double
     */
    public double getAmount() {
        return amount;
    }       
}
