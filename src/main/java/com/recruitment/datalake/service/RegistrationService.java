package com.recruitment.datalake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recruitment.datalake.dto.RegisterCandidateRequest;
import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.entities.Role;
import com.recruitment.datalake.entities.User;
import com.recruitment.datalake.repos.CandidateRepository;
import com.recruitment.datalake.repos.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerCandidate(RegisterCandidateRequest request) {
        System.out.println("Checking if email already exists...");
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        System.out.println("Creating User...");
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.CANDIDATE);

        userRepository.save(user);
        System.out.println("User saved with ID: " + user.getId());

        System.out.println("Creating Candidate...");
        Candidate candidate = new Candidate();
        candidate.setUser(user);

        candidateRepository.save(candidate);
        System.out.println("Candidate saved with ID: " + candidate.getId());
    }

}
