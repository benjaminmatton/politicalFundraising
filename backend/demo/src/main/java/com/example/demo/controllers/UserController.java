package com.example.demo.controllers;


import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller class for handling user authentication operations.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Handles user registration.
     * @param user The user object containing registration details
     * @return ResponseEntity with registration status message
     */
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Check if the email is already in use
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the new user to the database
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Handles user login.
     * @param user The user object containing login credentials
     * @return ResponseEntity with login status message
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Find the user by email
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        // Check if user exists and password matches
        if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
            // Generate JWT (not implemented in this code)
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    } //need to direct to personalization page
}