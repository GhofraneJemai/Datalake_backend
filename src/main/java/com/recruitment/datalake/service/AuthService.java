package com.recruitment.datalake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recruitment.datalake.dto.LoginRequest;
import com.recruitment.datalake.entities.User;
import com.recruitment.datalake.repos.UserRepository;
import com.recruitment.datalake.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginRequest request) {
        System.out.println("Attempting login for email: " + request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    System.out.println("User not found for email: " + request.getEmail());
                    return new RuntimeException("User not found");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("Invalid password for email: " + request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }

        
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        System.out.println("Token generated for email: " + user.getEmail());
        return token;
    }

}
