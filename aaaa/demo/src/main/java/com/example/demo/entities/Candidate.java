package com.example.demo.entities;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a political candidate in the system.
 * This class is mapped to the "candidates" collection in MongoDB.
 */
@Document(collection = "candidates")
public class Candidate {
    /** Unique identifier for the candidate */
    @Id
    private String id;
    
    /** Name of the candidate */
    private String name;
    
    /** Political party affiliation of the candidate */
    private String party;
    
    /** Brief description or biography of the candidate */
    private String description;
    
    /** URL to the candidate's image */
    private String imageUrl;
    
    /** Map of issue identifiers to the candidate's stance scores on those issues */
    private Map<String, Double> issueScores = new HashMap<>();

    // Getters and setters
    /**
     * Gets the candidate's unique identifier.
     * @return The candidate's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the candidate's unique identifier.
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the candidate's name.
     * @return The candidate's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the candidate's name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the candidate's political party.
     * @return The candidate's party
     */
    public String getParty() {
        return party;
    }

    /**
     * Sets the candidate's political party.
     * @param party The party to set
     */
    public void setParty(String party) {
        this.party = party;
    }

    /**
     * Gets the candidate's description.
     * @return The candidate's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the candidate's description.
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the URL of the candidate's image.
     * @return The candidate's image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL of the candidate's image.
     * @param imageUrl The image URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the map of the candidate's issue scores.
     * @return A map of issue identifiers to score values
     */
    public Map<String, Double> getIssueScores() {
        return issueScores;
    }

    /**
     * Sets the map of the candidate's issue scores.
     * @param issueScores The map of issue scores to set
     */
    public void setIssueScores(Map<String, Double> issueScores) {
        this.issueScores = issueScores;
    }
}
