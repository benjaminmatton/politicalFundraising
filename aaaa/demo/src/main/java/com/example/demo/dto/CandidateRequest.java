package com.example.demo.dto;

import java.util.Map;


public class CandidateRequest {

    private String name;
    private String party;
    private String description;
    private String imageUrl;
    private Map<String, Double> issueScores;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }   

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }   

    public Map<String, Double> getIssueScores() {
        return issueScores;
    }

    public void setIssueScores(Map<String, Double> issueScores) {
        this.issueScores = issueScores;
    }   
}
