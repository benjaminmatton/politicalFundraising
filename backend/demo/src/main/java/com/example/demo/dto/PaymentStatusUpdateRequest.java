package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) for updating payment status.
 * This class encapsulates the information needed to update the status of a payment.
 */
public class PaymentStatusUpdateRequest {
    /** The ID of the payment intent */
    private String paymentIntentId;
    /** The new status of the payment */
    private String status;

    /**
     * Retrieves the payment intent ID.
     * @return The payment intent ID as a String
     */
    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    /**
     * Sets the payment intent ID.
     * @param paymentIntentId The payment intent ID to set
     */
    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    /**
     * Retrieves the status of the payment.
     * @return The payment status as a String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the payment.
     * @param status The payment status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }       
}