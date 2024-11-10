package com.example.demo.dto;

import java.util.Map;

/**
 * Data Transfer Object (DTO) for handling candidate creation and update requests.
 * Used in CandidateController for POST and PUT endpoints (/api/candidates and /api/candidates/{id}).
 * Provides a clean interface for receiving candidate data from client requests without exposing internal entity structure.
 */
public class CandidateRequest {

    // Basic candidate information
    private String name;        // Name of the candidate
    private String party;       // Political party affiliation
    private String description; // Biographical description or summary
    private String imageUrl;    // URL to candidate's profile image
    
    // Map of issue positions where key is issue name and value is candidate's score/stance (0-100)
    private Map<String, Double> issueScores;

    /**
     * Gets the candidate's name
     * @return the candidate's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the candidate's name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the candidate's political party
     * @return the candidate's party affiliation
     */
    public String getParty() {
        return party;
    }

    /**
     * Sets the candidate's political party
     * @param party the party to set
     */
    public void setParty(String party) {
        this.party = party;
    }   

    /**
     * Gets the candidate's description
     * @return the biographical description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the candidate's description
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the URL of candidate's image
     * @return the image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL for candidate's image
     * @param imageUrl the image URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }   

    /**
     * Gets the map of candidate's positions on various issues
     * @return map of issue names to score values
     */
    public Map<String, Double> getIssueScores() {
        return issueScores;
    }

    /**
     * Sets the candidate's issue positions
     * @param issueScores map of issue names to score values
     */
    public void setIssueScores(Map<String, Double> issueScores) {
        this.issueScores = issueScores;
    }   
    
}
