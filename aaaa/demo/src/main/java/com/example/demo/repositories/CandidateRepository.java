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
     * Custom query method to find candidates by party affiliation.
     * 
     * @param party The political party to search for
     * @return A list of Candidate objects belonging to the specified party
     */
    List<Candidate> findByParty(String party);

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
}
