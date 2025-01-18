package com.recruitment.datalake;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.repos.JobPostRepository;
import com.recruitment.datalake.service.JobPostService;

@SpringBootTest
class JobPostServiceTest {

    @Autowired
    private JobPostRepository jobPostRepository;
    @Autowired
    private JobPostService jobPostService;

    @Test
    public void testCreateJobPost() {
        // Prepare test data
        List<Application> applications = new ArrayList<>();
        LocalDateTime postedAt = LocalDateTime.now();
        
        JobPost jobPost = new JobPost("Software Engineer", 
                                      "Develop and maintain software applications", 
                                      "New York, NY", 
                                      "Java, Spring Boot, SQL", 
                                      applications, 
                                      postedAt);
        
        JobPost savedJobPost = jobPostService.createJobPost(jobPost);
        
        // Assert that the saved job post is not null and has a valid ID
        assertNotNull(savedJobPost.getId());
        assertEquals("Software Engineer", savedJobPost.getTitle());
    }

    @Test
    public void testFindJobPostById() {
        JobPost jobPost = jobPostRepository.findById(1L).orElse(null);
        assertNotNull(jobPost);
    }

    @Test
    public void testUpdateJobPost() {
        JobPost jobPost = jobPostRepository.findById(1L).orElse(null);
        assertNotNull(jobPost);
        jobPost.setLocation("San Francisco, CA");
        jobPostRepository.save(jobPost);
        assertEquals("San Francisco, CA", jobPost.getLocation());
    }

    @Test
    public void testDeleteJobPost() {
        jobPostRepository.deleteById(1L);
        assertFalse(jobPostRepository.findById(1L).isPresent());
    }

    @Test
    public void testListAllJobPosts() {
        List<JobPost> jobPosts = jobPostRepository.findAll();
        assertFalse(jobPosts.isEmpty());
    }
}
