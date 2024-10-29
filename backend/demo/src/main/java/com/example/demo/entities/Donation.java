package com.example.demo.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Represents a donation made to a political candidate.
 * This class is mapped to the "donations" collection in MongoDB.
 */
@Document(collection = "donations")
public class Donation {
    /** Unique identifier for the donation */
    @Id
    private String id;
    /** ID of the user who made the donation */
    private String userId;
    /** ID of the candidate receiving the donation */
    private String candidateId;
    /** Amount of the donation */
    private double amount;
    /** Date when the donation was made */
    private Date date;
    /** ID of the payment intent (used for payment processing) */
    private String paymentIntentId;
    /** Status of the donation (e.g., "pending", "completed", "failed") */
    private String status;

    /**
     * Gets the status of the donation.
     * @return The status of the donation
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the donation.
     * @param status The status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and setters
    /**
     * Gets the unique identifier of the donation.
     * @return The donation's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the donation.
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user who made the donation.
     * @return The user's ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who made the donation.
     * @param userId The user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the candidate receiving the donation.
     * @return The candidate's ID
     */
    public String getCandidateId() {
        return candidateId;
    }

    /**
     * Sets the ID of the candidate receiving the donation.
     * @param candidateId The candidate ID to set
     */
    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * Gets the amount of the donation.
     * @return The donation amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the donation.
     * @param amount The amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the date when the donation was made.
     * @return The donation date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date when the donation was made.
     * @param date The date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the ID of the payment intent.
     * @return The payment intent ID
     */
    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    /**
     * Sets the ID of the payment intent.
     * @param paymentIntentId The payment intent ID to set
     */
    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }
}