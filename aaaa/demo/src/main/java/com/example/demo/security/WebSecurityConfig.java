package com.example.demo.security;

// Import necessary Spring Security and configuration classes
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for web security settings.
 * This class sets up the security filter chain for the application.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Configures the security filter chain for the application.
     *
     * @param http The HttpSecurity object to configure
     * @return The built SecurityFilterChain
     * @throws Exception If an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection
            .csrf().disable()
            // Configure authorization rules
            .authorizeHttpRequests(authorize -> authorize
                // Allow all requests to the authentication endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/candidates/**").permitAll()
                .requestMatchers("/api/issues/**").permitAll()
                .requestMatchers("/api/donations/**").permitAll()
                .requestMatchers("/api/payments/**").permitAll()
                // Require authentication for all other requests
                .anyRequest().authenticated()
            )
            // Configure session management
            .sessionManagement(session -> session
                // Use stateless session; no session will be created or used by spring security
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        
        // Build and return the security filter chain
        return http.build();
    }
}