package com.example.demo.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a political issue in the system.
 * This class is mapped to the "issues" collection in MongoDB.
 */
@Document(collection = "issues")
public class Issue {
    /** Unique identifier for the issue */
    @Id
    private String id;
    
    /** Name of the issue */
    private String name;
    
    /** List of candidate IDs associated with this issue */
    private List<String> candidateIds = new ArrayList<>();
    
    /** Map of candidates to their scores on this issue */
    private Map<Candidate, Double> candidateScores = new HashMap<>();

    public Issue(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the map of candidate scores for this issue.
     * @return A map of Candidate objects to their scores (as Double)
     */
    public Map<Candidate, Double> getCandidateScores() {
        return candidateScores;
    }

    /**
     * Sets the map of candidate scores for this issue.
     * @param candidateScores A map of Candidate objects to their scores (as Double)
     */
    public void setCandidateScores(Map<Candidate, Double> candidateScores) {
        this.candidateScores = candidateScores;
    }

    /**
     * Gets the unique identifier of the issue.
     * @return The issue's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the issue.
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the issue.
     * @return The issue's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the issue.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
