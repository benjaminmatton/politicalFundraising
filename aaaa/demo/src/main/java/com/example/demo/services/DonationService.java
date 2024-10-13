package com.example.demo.services;

// Import necessary classes and interfaces
import com.example.demo.entities.Donation;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.DonationRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service class for managing donation-related operations.
 */
@Service
public class DonationService {
    /**
     * Repository for Donation entities.
     */
    @Autowired
    private DonationRepository donationRepository;

    /**
     * Processes a donation.
     *
     * @param donation The donation to process
     * @return The processed and saved donation
     */
    public Donation processDonation(Donation donation) {
        // Business logic for processing a donation
        return donationRepository.save(donation);
    }

    /**
     * Stripe API key injected from application properties.
     */
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    /**
     * Constructor to initialize Stripe API key.
     */
    public DonationService() {
        Stripe.apiKey = stripeApiKey;
    }

    /**
     * Processes a donation and creates a payment intent with Stripe.
     *
     * @param userId The ID of the user making the donation
     * @param candidateId The ID of the candidate receiving the donation
     * @param amount The amount of the donation
     * @return The client secret for the created PaymentIntent
     * @throws StripeException If there's an error with the Stripe API
     */
    public String donate(String userId, String candidateId, double amount) throws StripeException {
        // 1. Create a PaymentIntent with Stripe
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount((long) (amount * 100)) // Amount in cents
            .setCurrency("usd") // Assuming USD; replace with dynamic value if needed
            .putAllMetadata(Map.of("userId", userId, "candidateId", candidateId))
            .build();   

        PaymentIntent intent = PaymentIntent.create(params);

        // 2. Save the donation with payment information (pending status)
        Donation donation = new Donation();
        donation.setUserId(userId);
        donation.setCandidateId(candidateId);
        donation.setAmount(amount);
        donation.setPaymentIntentId(intent.getId()); // Save the PaymentIntent ID
        donation.setStatus("pending"); // Status could be 'pending', 'succeeded', or 'failed'

        donationRepository.save(donation);

        // 3. Return the client secret for the frontend to confirm the payment
        return intent.getClientSecret();
    }

    /**
     * Updates the status of a donation after payment confirmation.
     *
     * @param paymentIntentId The ID of the PaymentIntent associated with the donation
     * @param status The new status of the donation
     * @throws ResourceNotFoundException If the donation is not found
     */
    public void updateDonationStatus(String paymentIntentId, String status) {
        Donation donation = donationRepository.findByPaymentIntentId(paymentIntentId)
            .orElseThrow(() -> new ResourceNotFoundException("Donation not found"));
        donation.setStatus(status);
        donationRepository.save(donation);
    }
}
