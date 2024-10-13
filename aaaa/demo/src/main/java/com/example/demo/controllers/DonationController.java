package com.example.demo.controllers;


import com.example.demo.entities.Donation;
import com.example.demo.repositories.DonationRepository;
import com.example.demo.services.DonationService;
import com.stripe.exception.StripeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.DonationRequest;
import com.example.demo.dto.PaymentStatusUpdateRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for handling donation-related HTTP requests.
 */
@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonationService donationService;

    /**
     * Creates a new donation.
     * @param donation The donation object to be created
     * @return The created donation
     */
    @PostMapping
    public Donation createDonation(@RequestBody Donation donation) {
        return donationRepository.save(donation);
    }

    /**
     * Retrieves all donations made by a specific user.
     * @param userId The ID of the user
     * @return List of donations made by the user
     */
    @GetMapping("/user/{userId}")
    public List<Donation> getDonationsByUserId(@PathVariable String userId) {
        return donationRepository.findByUserId(userId);
    }

    /**
     * Retrieves all donations made to a specific candidate.
     * @param candidateId The ID of the candidate
     * @return List of donations made to the candidate
     */
    @GetMapping("/candidate/{candidateId}")
    public List<Donation> getDonationsByCandidateId(@PathVariable String candidateId) {
        return donationRepository.findByCandidateId(candidateId);
    }

    /**
     * Processes a donation request and creates a Stripe PaymentIntent.
     * @param donationRequest The donation request containing user ID, candidate ID, and amount
     * @return ResponseEntity containing the client secret for the PaymentIntent
     */
    @PostMapping("/donate")
    public ResponseEntity<Map<String, String>> donate(@RequestBody DonationRequest donationRequest) {
        try {
            String clientSecret = donationService.donate(
                donationRequest.getUserId(),
                donationRequest.getCandidateId(),
                donationRequest.getAmount()
            );

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", clientSecret);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Updates the status of a donation based on the payment intent status.
     * @param request The payment status update request containing payment intent ID and status
     * @return ResponseEntity indicating the success of the update
     */
    @PostMapping("/update-status")
    public ResponseEntity<String> updateDonationStatus(@RequestBody PaymentStatusUpdateRequest request) {
        donationService.updateDonationStatus(request.getPaymentIntentId(), request.getStatus());
        return ResponseEntity.ok("Donation status updated");
    }
}