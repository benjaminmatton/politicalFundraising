package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }

    // private final JwtService jwtService;
    // private final UserDetailsService userDetailsService;

    // @Autowired
    // public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    //     this.jwtService = jwtService;
    //     this.userDetailsService = userDetailsService;
    // }

    // @Override
    // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    //         throws ServletException, IOException {
    //     String authorizationHeader = request.getHeader("Authorization");
    //     String jwt = null;
    //     String userId = null;

    //     if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
    //         jwt = authorizationHeader.substring(7);
    //         userId = jwtService.getUserIdFromToken(jwt);
    //     }

    //     if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    //         UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
    //         if (jwtService.validateToken(jwt)) {
    //             UsernamePasswordAuthenticationToken authenticationToken =
    //                 new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    //             authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    //             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    //         }
    //     }

    //     filterChain.doFilter(request, response);
    // }
}
