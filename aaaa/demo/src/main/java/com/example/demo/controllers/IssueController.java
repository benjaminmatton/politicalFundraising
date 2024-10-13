package com.example.demo.controllers;


import com.example.demo.entities.Candidate;
import com.example.demo.entities.Issue;
import com.example.demo.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller class for handling Issue-related HTTP requests.
 */
@RestController
@RequestMapping("/api")
public class IssueController {

    @Autowired
    private IssueService issueService;

    /**
     * Retrieves all issues.
     * @return List of all issues
     */
    @GetMapping("/issues")
    public List<Issue> getAllIssues() {
        return issueService.findAllIssues();
    }

    /**
     * Retrieves effective candidates for a specific issue.
     * @param issueName The name of the issue
     * @return ResponseEntity containing a map of candidates and their effectiveness scores
     */
    @GetMapping("/issues/{issueName}/candidates")
    public ResponseEntity<Map<Candidate, Double>> getEffectiveCandidates(@PathVariable String issueName) {
        Map<Candidate, Double> candidates = issueService.getEffectiveCandidatesForIssue(issueName);
        return ResponseEntity.ok(candidates);
    }
}