package com.example.demo.repositories;

import com.example.demo.entities.Issue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Issue entities.
 * Extends MongoRepository to provide CRUD operations for Issue documents.
 */
@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {

    /**
     * Finds all issues belonging to a specific category.
     *
     * @param category The category to search for
     * @return A list of Issues that belong to the specified category
     */
    List<Issue> findByCategory(String category);

    /**
     * Finds an issue by its name.
     * @param name The name of the issue to search for
     * @return An Optional containing the Issue if found, or empty if not found
     */
    Optional<Issue> findByName(String name);

    /**
     * Creates a new issue.
     * @param issue The issue object to be created
     * @return The created issue
     */
    Issue createIssue(Issue issue);
}
