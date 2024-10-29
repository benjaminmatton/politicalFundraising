package com.example.demo.services;

import com.example.demo.entities.Issue;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing user-related operations.
 */
@Service
public class UserService {

    /**
     * Repository for User entities.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Password encoder for securely hashing user passwords.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user with selected issues.
     *
     * @param user The user to register
     * @param selectedIssues The set of issues selected by the user
     */
    public void registerUser(User user, Set<Issue> selectedIssues) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIssues(selectedIssues); // Store selected issues
        userRepository.save(user);
    }

    /**
     * Authenticates a user by checking the provided credentials.
     *
     * @param username The user's email address used as username
     * @param rawPassword The user's password in plain text
     * @return An Optional containing the authenticated User if successful, or empty if authentication fails
     */
    public Optional<User> authenticateUser(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find
     * @return An Optional containing the User if found, or empty if not found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves the set of issues associated with a user.
     *
     * @param userId The ID of the user
     * @return A Set of Issue entities associated with the user, or an empty set if the user is not found
     */
    public Set<Issue> getUserIssues(String userId) {
        return userRepository.findById(userId)
            .map(User::getIssues)
            .orElse(Collections.emptySet());
    }
}
