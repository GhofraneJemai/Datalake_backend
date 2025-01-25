package com.recruitment.datalake;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.entities.Role;
import com.recruitment.datalake.entities.User;
import com.recruitment.datalake.repos.ApplicationRepository;
import com.recruitment.datalake.repos.CandidateRepository;
import com.recruitment.datalake.repos.JobPostRepository;
import com.recruitment.datalake.repos.UserRepository;
import com.recruitment.datalake.service.ApplicationService;

import jakarta.transaction.Transactional;

@SpringBootTest
class ApplicationServiceTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testApplyForJob() {
        // Prepare test data for User first
        User user = new User("hibatroudi835@gmail.com", "password123", "Hiba", "Troudi", Role.CANDIDATE); // Assuming Role is an enum, and 'CANDIDATE' is a valid role
        user = userRepository.save(user);  // Save the user first

        // Create a Candidate linked to the User
        Candidate candidate = new Candidate(user, new ArrayList<>());
        candidate = candidateRepository.save(candidate);

        // Create a JobPost
        JobPost jobPost = new JobPost("Software Engineer", 
                                      "Develop software applications", 
                                      "San Francisco, CA", 
                                      "Java, Spring Boot, SQL", 
                                      new ArrayList<>(), 
                                      LocalDateTime.now());
        jobPost = jobPostRepository.save(jobPost);

        // Prepare cover letter and CV URL
        String coverLetter = "I am very interested in this position and believe I am a good fit.";
        String cvUrl = "http://example.com/cv/bob_johnson.pdf";

        // Apply for the job
        Application application = applicationService.applyForJob(candidate.getId(), jobPost.getId(), coverLetter, cvUrl);

        // Assertions
        assertNotNull(application.getId());
        assertEquals(candidate.getId(), application.getCandidate().getId());
        assertEquals(jobPost.getId(), application.getJobPost().getId());
        assertEquals("PENDING", application.getStatus());
        assertEquals(coverLetter, application.getCoverLetter());
        assertEquals(cvUrl, application.getCvUrl());
        assertNotNull(application.getCreatedAt());
    }

    @Test
    public void testFindApplicationById() {
        Application application = applicationRepository.findById(1L).orElse(null);
        assertNotNull(application);
    }

    @Test
    public void testUpdateApplicationStatus() {

        // Update application status
        LocalDateTime recruitmentDate = LocalDateTime.of(2025, 2, 15, 9, 0);
        Application updatedApplication = applicationService.updateApplicationStatus(2L, "APPROVED", recruitmentDate);

        // Assertions
        assertNotNull(updatedApplication);
        assertEquals("APPROVED", updatedApplication.getStatus());
    }

    @Test
    public void testDeleteApplication() {
        applicationRepository.deleteById(1L);
        assertFalse(applicationRepository.findById(1L).isPresent());
    }

    @Test
    public void testListAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        assertFalse(applications.isEmpty());
    }
}
