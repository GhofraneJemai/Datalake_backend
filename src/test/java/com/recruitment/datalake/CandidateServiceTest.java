package com.recruitment.datalake;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.repos.CandidateRepository;
import com.recruitment.datalake.service.CandidateService;

@SpringBootTest
class CandidateServiceTest {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private CandidateService candidateService;

    @Test
    public void testRegisterCandidate() {
        List<Application> applications = new ArrayList<>();
        Candidate candidate = new Candidate("Alice", "Smith", "alice.smith@example.com", "1234567890", applications);
        Candidate savedCandidate = candidateService.saveCandidate(candidate);
        assertNotNull(savedCandidate.getId());
        
    }

    @Test
    public void testFindCandidateById() {
        Candidate candidate = candidateRepository.findById(1L).orElse(null);
        assertNotNull(candidate);
        System.out.println(candidate.getId());
        System.out.println(candidate);
    }

    @Test
    public void testCreateCandidate() {
        Candidate candidate = new Candidate("John", "Doe", "john.doe@example.com", "1234567890", new ArrayList<>());
        candidateRepository.save(candidate);
        assertNotNull(candidate.getId());
    }


    @Test
    public void testUpdateCandidate() {
        Candidate candidate = candidateRepository.findById(1L).orElse(null);
        assertNotNull(candidate);
        candidate.setPhone("0987654321");
        candidateRepository.save(candidate);

        Candidate updatedCandidate = candidateRepository.findById(1L).get();
        assertEquals("0987654321", updatedCandidate.getPhone());
    }

    @Test
    public void testDeleteCandidate() {
        candidateRepository.deleteById(1L);
        Candidate candidate = candidateRepository.findById(1L).orElse(null);
        assertNull(candidate);
    }

    @Test
    public void testListAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        assertFalse(candidates.isEmpty());
        candidates.forEach(System.out::println);
    }

    @Test
    public void testFindByFirstName() {
        List<Candidate> candidates = candidateRepository.findByFirstName("Alice");
        candidates.forEach(System.out::println);
    }

    @Test
    public void testFindByLastName() {
        List<Candidate> candidates = candidateRepository.findByLastName("Smith");
        candidates.forEach(System.out::println);
    }

    @Test
    public void testFindByEmail() {
        List<Candidate> candidates = candidateRepository.findByEmail("alice.smith@example.com");
        candidates.forEach(System.out::println);
    }


}
