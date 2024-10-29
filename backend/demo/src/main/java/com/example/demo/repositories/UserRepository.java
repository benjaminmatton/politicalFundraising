package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * Extends MongoRepository to provide CRUD operations for User documents.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Custom query method to find a user by their email address.
     *
     * @param email The email address to search for
     * @return An Optional containing the User if found, or an empty Optional if not found
     */
    Optional<User> findByEmail(String email);

}
