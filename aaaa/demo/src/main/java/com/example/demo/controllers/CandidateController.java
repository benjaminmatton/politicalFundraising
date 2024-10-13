package com.example.demo.controllers;


import com.example.demo.entities.Candidate;
import com.example.demo.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling Candidate-related HTTP requests.
 */
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * Retrieves all candidates.
     * @return List of all candidates
     */
    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    /**
     * Retrieves candidates by their name.
     * @param name The name of the candidate to search for
     * @return List of candidates matching the given name
     */
    @GetMapping("/search")
    public List<Candidate> getCandidatesByName(@RequestParam String name) {
        return candidateRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Retrieves a candidate by their ID.
     * @param id The ID of the candidate
     * @return ResponseEntity containing the candidate if found, or a not found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable String id) {
        return candidateRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves candidates by their party affiliation.
     * @param party The party name
     * @return List of candidates belonging to the specified party
     */
    @GetMapping("/party/{party}")
    public List<Candidate> getCandidatesByParty(@PathVariable String party) {
        return candidateRepository.findByParty(party);
    }

    /**
     * Creates a new candidate.
     * @param candidate The candidate object to be created
     * @return The created candidate
     */
    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    /**
     * Updates an existing candidate.
     * @param id The ID of the candidate to update
     * @param candidateDetails The updated candidate details
     * @return ResponseEntity containing the updated candidate if found, or a not found status
     */
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable String id, @RequestBody Candidate candidateDetails) {
        return candidateRepository.findById(id)
            .map(candidate -> {
                candidate.setName(candidateDetails.getName());
                candidate.setParty(candidateDetails.getParty());
                candidate.setDescription(candidateDetails.getDescription());
                candidate.setImageUrl(candidateDetails.getImageUrl());
                return ResponseEntity.ok(candidateRepository.save(candidate));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a candidate.
     * @param id The ID of the candidate to delete
     * @return ResponseEntity with no content if successful, or a not found status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCandidate(@PathVariable String id) {
        return candidateRepository.findById(id)
            .map(candidate -> {
                candidateRepository.delete(candidate);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
