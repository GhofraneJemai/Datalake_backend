package com.recruitment.datalake;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.repos.ApplicationRepository;
import com.recruitment.datalake.repos.CandidateRepository;
import com.recruitment.datalake.repos.JobPostRepository;
import com.recruitment.datalake.service.ApplicationService;

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

    @Test
    public void testApplyForJob() {
        // Prepare test data
        Candidate candidate = new Candidate("Samia", "Jmii", "samiajmeii@gmail.com", "0987654321", new ArrayList<>());
        candidate = candidateRepository.save(candidate);
        
        JobPost jobPost = new JobPost("Software Engineer", 
                                      "Develop software applications", 
                                      "San Francisco, CA", 
                                      "Java, Spring Boot, SQL", 
                                      new ArrayList<>(), 
                                      LocalDateTime.now());
        jobPost = jobPostRepository.save(jobPost);

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
        Application updatedApplication = applicationService.updateApplicationStatus(18L, "APPROVED", recruitmentDate);

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
