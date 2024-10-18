package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Candidate;
import com.example.demo.entities.Issue;
import com.example.demo.repositories.CandidateRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.dto.CandidateRequest;
import java.util.Optional;

/**
 * Service class for managing Candidate-related operations.
 */
@Service
public class CandidateService {
    /**
     * Repository for Candidate entities.
     */
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private IssueRepository issueRepository;


    /**
     * Updates the score of a candidate for a specific issue.
     *
     * @param candidateId The ID of the candidate to update
     * @param issueId The ID of the issue to update the score for
     * @param score The new score for the issue
     * @throws RuntimeException if the candidate is not found
     */
    public void updateCandidateScoreForIssue(String candidateId, String issueId, double score) {
        Candidate candidate = candidateRepository.findById(candidateId)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));
        
        candidate.getIssueScores().put(issueId, score);
        candidateRepository.save(candidate);
    }

    /**
     * Retrieves a candidate by their ID.
     *
     * @param candidateId The ID of the candidate to retrieve
     * @return The Candidate entity
     * @throws ResourceNotFoundException if the candidate is not found
     */
    public Candidate getCandidateById(String candidateId) {
        return candidateRepository.findById(candidateId)
            .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateId));
    }

    /**
     * Retrieves all candidates sorted by their score for a specific issue.
     *
     * @param issueId The ID of the issue to sort by
     * @return A list of Candidate entities sorted by their score for the given issue (descending order)
     */
    public List<Candidate> getCandidatesByIssueScore(String issueId) {
        List<Candidate> candidates = candidateRepository.findAll();
        candidates.sort((c1, c2) -> Double.compare(
            c2.getIssueScores().getOrDefault(issueId, 0.0),
            c1.getIssueScores().getOrDefault(issueId, 0.0)
        ));
        return candidates;
    }

    /**
     * Retrieves candidates by their name.
     */
    public List<Candidate> getCandidateByName(String name) {
        return candidateRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Adds a new candidate to the repository.
     *
     * @param candidate The candidate to add
     */
    public Candidate addCandidate(CandidateRequest candidate) {
        Candidate newCandidate = new Candidate(candidate.getName(), candidate.getParty(), candidate.getIssueScores());
        candidateRepository.save(newCandidate);
        return newCandidate;
    }

    /**
     * Updates a candidate's information.
     *
     * @param candidateId The ID of the candidate to update
     * @param dto The request object containing the updated candidate information
     * @return The updated candidate
     * @throws ResourceNotFoundException if the candidate is not found
     */
    public Candidate updateCandidate(String candidateId, CandidateRequest dto) {
        Candidate existingCandidate = candidateRepository.findById(candidateId)
            .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateId));
    
        // Only update non-null fields
        if (dto.getName() != null) {
            existingCandidate.setName(dto.getName());
        }
        if (dto.getParty() != null) {
            existingCandidate.setParty(dto.getParty());
        }
        if (dto.getDescription() != null) {
            existingCandidate.setDescription(dto.getDescription());
        }
        if (dto.getImageUrl() != null) {
            existingCandidate.setImageUrl(dto.getImageUrl());
        }
    
        // Save and return the updated candidate
        return candidateRepository.save(existingCandidate);
    }

    /**
     * Deletes a candidate by their unique identifier.
     * @param candidateId The ID of the candidate to delete
     */
    public void deleteCandidate(String candidateId) {  
        candidateRepository.deleteById(candidateId);
    }

    /**
     * Retrieves all candidates.
     * @return List of all candidates
     */
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    /**
     * Updates the candidate scores for each issue.
     */
    public void updateIssueCandidateScores() {
        // 1. Fetch all candidates
        List<Candidate> candidates = candidateRepository.findAll();
    
        // 2. Iterate through each candidate
        for (Candidate candidate : candidates) {
            // Get the candidate's issue scores
            Map<String, Double> issueScores = candidate.getIssueScores();
    
            // 3. Iterate through the issue scores of the candidate
            for (Map.Entry<String, Double> entry : issueScores.entrySet()) {
                String issueName = entry.getKey(); // Get the issue name
                Double score = entry.getValue(); // Get the candidate's score for this issue
    
                // 4. Retrieve the existing Issue from the database
                Optional<Issue> issue = issueRepository.findByName(issueName);
                if (issue.isPresent()) {
                    // Update the candidateScores map in the existing Issue object
                    Map<String, Double> candidateScores = issue.get().getCandidateScores();
                    candidateScores.put(candidate.getName(), score); // Use candidate's name as key
    
                    // Save the updated issue to the database
                    issueRepository.save(issue.get());
                } else {
                    System.out.println("Issue not found: " + issueName);
                }
            }
        }
    }
}
