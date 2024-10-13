package com.example.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a user in the system.
 * This class is mapped to the "users" collection in MongoDB.
 */
@Document(collection = "users")
public class User {
    /** Unique identifier for the user */
    @Id
    private String id;
    /** User's email address */
    private String email;
    /** User's password (should be encrypted before storage) */
    private String password;
    /** Set of issues the user is interested in */
    private Set<Issue> issues = new HashSet<>();

    // Getters
    /**
     * Gets the user's unique identifier.
     * @return The user's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the user's email address.
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's password.
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the set of issues the user is interested in.
     * @return A Set of Issue objects
     */
    public Set<Issue> getIssues() {
        return issues;
    }

    // Setters
    /**
     * Sets the user's unique identifier.
     * @param id The ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the user's email address.
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's interested issues.
     * @param issues A Set of Issue objects to set
     */
    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

}
