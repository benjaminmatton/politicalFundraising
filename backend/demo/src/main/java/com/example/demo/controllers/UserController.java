package com.example.demo.controllers;


import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.LoginResponse;
import java.util.Optional;
import com.example.demo.security.JwtService;
import java.util.Collections;
import com.example.demo.dto.TokenResponse;
import com.example.demo.dto.ErrorResponse;  
import org.springframework.http.HttpStatus;

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

    @Autowired
    private JwtService jwtService;

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
        user.setRoles(Collections.singleton("USER"));
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
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        
        if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
            String accessToken = jwtService.generateAccessToken(foundUser.get().getEmail());
            String refreshToken = jwtService.generateRefreshToken(foundUser.get().getEmail());
            
            TokenResponse tokenResponse = new TokenResponse(
                accessToken,
                refreshToken,
                jwtService.getAccessTokenExpiration()
            );
            
            return ResponseEntity.ok(tokenResponse);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse("Invalid username or password"));
    } //need to direct to personalization page

    
}