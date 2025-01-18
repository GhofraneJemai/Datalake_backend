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
}
