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
}
