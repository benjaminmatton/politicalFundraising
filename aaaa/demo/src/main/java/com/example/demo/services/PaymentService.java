package com.example.demo.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for handling payment-related operations using Stripe.
 */
@Service
public class PaymentService {

    /**
     * Stripe API key injected from application properties.
     */
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    /**
     * Constructor to initialize Stripe API key.
     */
    public PaymentService() {
        Stripe.apiKey = stripeApiKey;
    }

    /**
     * Creates a PaymentIntent to handle the donation.
     *
     * @param amount The donation amount
     * @param currency The currency of the donation
     * @return The client secret for the created PaymentIntent
     * @throws StripeException If there's an error with the Stripe API
     */
    public String createPaymentIntent(double amount, String currency, String candidateId, String userId) throws StripeException {
        // Create parameters for the PaymentIntent
        PaymentIntentCreateParams params =
            PaymentIntentCreateParams.builder()
                .setAmount((long) (amount * 100)) // Convert amount to cents
                .setCurrency(currency)
                .putMetadata("candidateId", candidateId)  // Store candidate info
                .putMetadata("userId", userId)  // Track the donor
                .putMetadata("timestamp", String.valueOf(System.currentTimeMillis()))  // Store creation time
                .build();

        // Create the PaymentIntent using Stripe API
        PaymentIntent intent = PaymentIntent.create(params);
        
        // Return the client secret for the frontend to use
        return intent.getClientSecret();
    }

    
}