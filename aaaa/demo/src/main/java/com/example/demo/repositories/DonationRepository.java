package com.example.demo.repositories;

import com.example.demo.entities.Donation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends MongoRepository<Donation, String> {
    // Find all donations made by a specific user
    List<Donation> findByUserId(String userId);
    
    // Find all donations made to a specific candidate
    List<Donation> findByCandidateId(String candidateId);

    Optional<Donation> findByPaymentIntentId(String paymentIntentId);
}