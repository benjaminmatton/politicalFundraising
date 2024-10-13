package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Candidate;
import com.example.demo.repositories.CandidateRepository;
import com.example.demo.exceptions.ResourceNotFoundException;

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
    public List<Candidate> getCandidatesByName(String name) {
        return candidateRepository.findByNameContainingIgnoreCase(name);
    }
}
