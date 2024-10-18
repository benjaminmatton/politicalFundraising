package com.example.demo.controllers;

import com.example.demo.services.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.Event;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.services.DonationService;
import java.util.HashMap;
import java.util.Map;


// Controller for handling payment-related API endpoints
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DonationService donationService;

    // Endpoint for creating a payment intent
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Create a payment intent using the PaymentService
            String clientSecret = paymentService.createPaymentIntent(paymentRequest.getAmount(), paymentRequest.getCurrency(), paymentRequest.getCandidateId(), paymentRequest.getUserId());
            
            // Prepare the response with the client secret
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", clientSecret);
            
            // Return the client secret in the response
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            // Handle Stripe-related exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    /**
     * Handles Stripe webhook events for payment processing.
     *
     * @param payload The raw payload of the webhook event
     * @param sigHeader The Stripe signature header for verification
     * @return ResponseEntity with the result of webhook processing
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            // Verify the webhook signature
            Event event = Webhook.constructEvent(
                payload,
                sigHeader,
                webhookSecret
            );

            // Handle the event type
            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();
                String paymentIntentId = paymentIntent.getId();

                // Update the donation status based on the payment intent
                donationService.updateDonationStatus(paymentIntentId, "succeeded");
            } else if ("payment_intent.payment_failed".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();
                String paymentIntentId = paymentIntent.getId();

                // Update the donation status to failed
                donationService.updateDonationStatus(paymentIntentId, "failed");
            }

            return ResponseEntity.ok("Webhook received");
        } catch (SignatureVerificationException e) {
            // Return bad request status if signature verification fails
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            // Return internal server error status for any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook processing error");
        }
    }
}