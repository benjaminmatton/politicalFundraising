package com.example.demo.controllers;


import com.example.demo.dto.CandidateRequest;
import com.example.demo.entities.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.CandidateService;

import java.util.List;

/**
 * Controller class for handling Candidate-related HTTP requests.
 */
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {


    @Autowired
    private CandidateService candidateService;

    /**
     * Retrieves all candidates.
     * @return ResponseEntity containing a list of all candidates
     */
    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidates);
    }

    /**
     * Retrieves candidates by their name.
     * @param name The name of the candidate to search for
     * @return ResponseEntity containing a list of candidates matching the given name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Candidate>> getCandidateByName(@RequestParam String name) {
        List<Candidate> candidates = candidateService.getCandidateByName(name);
        return ResponseEntity.ok(candidates);
    }

    /**
     * Retrieves a candidate by their ID.
     * 
     * @param id The ID of the candidate
     * @return ResponseEntity containing the candidate if found, or a not found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable String id) {
        Candidate candidate = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidate);  // Automatically returns 200 OK with the candidate
    }

    /**
     * Creates a new candidate.
     * @param candidate The candidate object to be created
     * @return ResponseEntity containing the created candidate
     */
    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody CandidateRequest candidate) {
        Candidate createdCandidate = candidateService.addCandidate(candidate);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    /**
     * Updates a candidate.
     * @param id The ID of the candidate to update
     * @param candidateDetails The updated candidate details
     * @return ResponseEntity containing the updated candidate
     */
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(
            @PathVariable String id, 
            @RequestBody CandidateRequest candidate) {

        Candidate updatedCandidate = candidateService.updateCandidate(id, candidate);
        return ResponseEntity.ok(updatedCandidate);
    }

    /**
     * Deletes a candidate.
     * 
     * @param id The ID of the candidate to delete
     * @return ResponseEntity with no content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable String id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    @PutMapping("/update-scores")
    public ResponseEntity<String> updateScores() {
        candidateService.updateIssueCandidateScores();
        return ResponseEntity.ok("Candidate scores updated successfully.");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllCandidates() {
        candidateService.deleteAllCandidates();
        return ResponseEntity.noContent().build();
    }

}
