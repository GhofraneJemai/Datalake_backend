package com.recruitment.datalake.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.repos.JobPostRepository;

@Service
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostServiceImpl(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    public JobPost createJobPost(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    @Override
    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }
    @Override
    public JobPost getJobPostById(Long id) {
        return jobPostRepository.findById(id).orElse(null);
    }

    @Override
    public JobPost updateJobPost(Long id, JobPost jobPost) {
        JobPost existingJobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobPost not found with ID: " + id));
        existingJobPost.setTitle(jobPost.getTitle());
        existingJobPost.setDescription(jobPost.getDescription());
        existingJobPost.setLocation(jobPost.getLocation());
        existingJobPost.setRequirements(jobPost.getRequirements());
        return jobPostRepository.save(existingJobPost);
    }

    @Override
    public void deleteJobPost(Long id) {
        if (!jobPostRepository.existsById(id)) {
            throw new RuntimeException("JobPost not found with ID: " + id);
        }
        jobPostRepository.deleteById(id);
    }
}
