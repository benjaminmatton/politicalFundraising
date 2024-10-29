package com.example.demo.services;

import com.example.demo.entities.Candidate;
import com.example.demo.entities.Issue;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.CandidateRepository;

// import org.apache.logging.log4j.util.PropertySource.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
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

    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * Retrieves all issues from the repository.
     *
     * @return A list of all Issue entities
     */
    public List<Issue> findAllIssues() {
        return issueRepository.findAll();
    }

    // /**
    //  * Calculates the popularity of issues based on their occurrence.
    //  * need to make it so  ....
    //  * @return A map where the key is the issue name and the value is the count of occurrences
    //  */
    // public Map<String, Long> getIssuePopularity() {
    //     return issueRepository.findAll().stream()
    //         .collect(Collectors.groupingBy(Issue::getName, Collectors.counting()));
    // }

    /**
     * Retrieves the effective candidates for a specific issue along with their scores.
     *
     * @param issueName The name of the issue to get candidates for
     * @return A list of Candidate objects with their scores for the issue,
     *         or an empty list if the issue is not found
     */
    public List<Candidate> getEffectiveCandidatesForIssue(String issueName) {
        // Fetch the candidate scores for the specified issue
        Map<String, Double> candidateScores = getCandidateScoresForIssue(issueName);
        
        // Sort by score in descending order, limit to top 10, and return the result as a list of Candidates
        return candidateScores.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())) // Sort by score descending
            .limit(10) // Limit to top 10 candidates
            .map(entry -> {
                Candidate candidate = candidateRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + entry.getKey()));
                candidate.getIssueScores().put(issueName, entry.getValue()); // Set the score for this issue
                return candidate;
            })
            .collect(Collectors.toList());
    }

    /**
     * Deletes all issues from the database.
     */
    public void deleteAllIssues() {
        // Fetch all issues
        List<Issue> issues = issueRepository.findAll();

        // Iterate through each issue and delete it
        for (Issue issue : issues) {
            issueRepository.delete(issue);
        }
    }

    public Optional<Issue> findByName(String name) {
        return issueRepository.findByName(name);
    }

    public Map<String, Double> getCandidateScoresForIssue(String issueName) {
        Optional<Issue> issue = issueRepository.findByName(issueName);
        return issue.map(Issue::getCandidateScores).orElse(Collections.emptyMap());
    }
}
