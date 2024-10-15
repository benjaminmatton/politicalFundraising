package com.example.demo.repositories;

import com.example.demo.entities.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Candidate entity.
 * Extends MongoRepository to provide CRUD operations for Candidate documents.
 */
@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

    /**
     * Finds a candidate by their unique identifier.
     * 
     * @param candidateId The ID of the candidate to find
     * @return An Optional containing the Candidate if found, or empty if not found
     */
    Optional<Candidate> findById(String candidateId);

    /**
     * Finds candidates by their name, ignoring case.
     */
    List<Candidate> findByNameContainingIgnoreCase(String name);

    /**
     * Creates a new candidate.
     * @param candidate The candidate object to be created
     * @return The created candidate
     */
    Candidate createCandidate(Candidate candidate);

    /**
     * Updates an existing candidate.
     * @param candidate The candidate object to be updated
     * @return The updated candidate
     */
    Candidate updateCandidate(Candidate candidate);

    /**
     * Deletes a candidate by their unique identifier.
     * @param candidateId The ID of the candidate to delete
     */
    void deleteCandidate(String candidateId);

    
}
