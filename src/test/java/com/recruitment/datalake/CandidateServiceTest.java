package com.recruitment.datalake;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.repos.CandidateRepository;
import com.recruitment.datalake.service.CandidateService;

import jakarta.transaction.Transactional;

import com.recruitment.datalake.entities.Role;
import com.recruitment.datalake.entities.User;
import com.recruitment.datalake.repos.UserRepository;
@SpringBootTest
class CandidateServiceTest {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private CandidateService candidateService;
    
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testRegisterCandidate() {
        // Create a User object for the candidate
        User user = new User("kam.smith@example.com", "password123", "Alice", "Smith", Role.CANDIDATE);

        // Save the User object (assuming you have a User service/repository for saving)
        user = userRepository.save(user);
        System.out.println("User saved with ID: " + user.getId());

        // Create a Candidate object associated with the saved User
        List<Application> applications = new ArrayList<>();
        System.out.println("Creating Candidate...");
        Candidate candidate = new Candidate(user, applications);
        candidate = candidateRepository.save(candidate);
        System.out.println("Candidate saved with ID: " + candidate.getId());

        // Flush to ensure data is persisted
        // Ensure User is properly associated
    }


    @Test
    public void testFindCandidateById() {
        Candidate candidate = candidateRepository.findById(1L).orElse(null);
        assertNotNull(candidate);
        System.out.println(candidate.getId());
        System.out.println(candidate);
    }






    @Test
    public void testDeleteCandidate() {
        candidateRepository.deleteById(1L);
        Candidate candidate = candidateRepository.findById(1L).orElse(null);
        assertNull(candidate);
    }
    @Transactional
    @Rollback(false)
    @Test
    public void testListAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        assertFalse(candidates.isEmpty());
        candidates.forEach(System.out::println);
    }

    @Test
    public void testFindByFirstName() {
        List<Candidate> candidates = candidateRepository.findByUserFirstName("Alice");
        candidates.forEach(System.out::println);
    }



}
