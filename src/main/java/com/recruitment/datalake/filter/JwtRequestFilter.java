package com.recruitment.datalake.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.recruitment.datalake.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractClaims(token).getSubject();
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.isTokenValid(token, email)) {
                // Decode the JWT token
                DecodedJWT decodedJWT = jwtUtil.decodeJWT(token);

                // Extract the role(s) from the JWT token claims
                String roleClaim = decodedJWT.getClaim("role").asString(); // Get role as String
                List<SimpleGrantedAuthority> authorities = null;

                // If the roleClaim is a single role
                if (roleClaim != null) {
                    authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleClaim));
                } else {
                    // If roles are stored as a list of strings, extract them
                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                    if (roles != null) {
                        authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());
                    }
                }

                if (authorities != null) {
                    // Create the Authentication object
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(email, null, authorities);

                    // Set the authentication object in SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
