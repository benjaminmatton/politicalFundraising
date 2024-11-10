package com.example.demo.controllers;


import com.example.demo.entities.Donation;
import com.example.demo.services.DonationService;
import com.stripe.exception.StripeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.DonationRequest;
import com.example.demo.dto.PaymentStatusUpdateRequest;
import com.example.demo.services.PaymentService;
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
    private PaymentService paymentService;
    @Autowired
    private DonationService donationService;

    /**
     * Retrieves all donations made by a specific user.
     * @param userId The ID of the user
     * @return ResponseEntity containing a list of donations made by the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Donation>> getDonationsByUserId(@PathVariable String userId) {
        List<Donation> donations = donationService.getDonationsByUserId(userId);
        return ResponseEntity.ok(donations);
    }

    /**
     * Retrieves all donations made to a specific candidate.
     * @param candidateId The ID of the candidate
     * @return ResponseEntity containing a list of donations made to the candidate
     */
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Donation>> getDonationsByCandidateId(@PathVariable String candidateId) {
        List<Donation> donations = donationService.getDonationsByCandidateId(candidateId);
        return ResponseEntity.ok(donations);
    }
    
    /**
     * Handles the donation process.
     * @param donationRequest The donation request containing amount, user ID, and candidate ID
     * @return ResponseEntity containing the client secret for the payment intent
     */
    @PostMapping("/donate")
    public ResponseEntity<Map<String, String>> donate(@RequestBody DonationRequest donationRequest) {
        try {
            Donation donation = new Donation();
            donation.setAmount(donationRequest.getAmount());
            donation.setUserId(donationRequest.getUserId());
            donation.setCandidateId(donationRequest.getCandidateId());

            String clientSecret = donationService.donate(donation);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", clientSecret);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Payment processing failed."));
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