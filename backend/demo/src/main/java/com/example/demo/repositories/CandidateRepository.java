package com.example.demo.repositories;

import com.example.demo.entities.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Candidate entity.
 * Extends MongoRepository to provide CRUD operations for Candidate documents.
 */
@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

    /**
     * Finds candidates by their name, ignoring case.
     */
    List<Candidate> findByNameContainingIgnoreCase(String name);

    
}
