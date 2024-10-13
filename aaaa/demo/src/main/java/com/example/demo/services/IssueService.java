package com.example.demo.services;

import com.example.demo.entities.Candidate;
import com.example.demo.entities.Issue;
import com.example.demo.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * Service class for managing Issue-related operations.
 */
@Service
public class IssueService {

    /**
     * Repository for Issue entities.
     */
    @Autowired
    private IssueRepository issueRepository;

    /**
     * Retrieves all issues from the repository.
     *
     * @return A list of all Issue entities
     */
    public List<Issue> findAllIssues() {
        return issueRepository.findAll();
    }

    /**
     * Finds issues belonging to a specific category.
     *
     * @param category The category to search for
     * @return A list of Issue entities that belong to the specified category
     */
    public List<Issue> findIssuesByCategory(String category) {
        return issueRepository.findByCategory(category);
    }

    /**
     * Calculates the popularity of issues based on their occurrence.
     *
     * @return A map where the key is the issue name and the value is the count of occurrences
     */
    public Map<String, Long> getIssuePopularity() {
        return issueRepository.findAll().stream()
            .collect(Collectors.groupingBy(Issue::getName, Collectors.counting()));
    }

    /**
     * Retrieves the effective candidates for a specific issue along with their scores.
     *
     * @param issueName The name of the issue to get candidates for
     * @return A map where the key is a Candidate and the value is their score for the issue,
     *         or an empty map if the issue is not found
     */
    public Map<Candidate, Double> getEffectiveCandidatesForIssue(String issueName) {
        return issueRepository.findByName(issueName)
            .map(Issue::getCandidateScores)
            .orElse(Collections.emptyMap());
    }
}