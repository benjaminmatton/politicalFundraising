package com.example.demo.entities;

import java.util.HashMap;
import java.util.Map;

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
    private String _id;
    
    /** Name of the issue */
    private String name;
    
    /** Map of candidates to their scores on this issue */
    private Map<String, Double> candidateScores = new HashMap<>();

    public Issue(String name) {
        this.name = name;
    }

    /**
     * Gets the map of candidate scores for this issue.
     * @return A map of Candidate objects to their scores (as Double)
     */
    public Map<String, Double> getCandidateScores() {
        return candidateScores;
    }

    /**
     * Sets the map of candidate scores for this issue.
     * @param candidateScores A map of Candidate objects to their scores (as Double)
     */
    public void setCandidateScores(Map<String, Double> candidateScores) {
        this.candidateScores = candidateScores;
    }

    /**
     * Gets the unique identifier of the issue.
     * @return The issue's ID
     */
    public String getId() {
        return _id;
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
