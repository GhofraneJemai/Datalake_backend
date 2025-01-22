package com.recruitment.datalake.service;

import java.util.List;

import com.recruitment.datalake.entities.JobPost;

public interface JobPostService {

    JobPost createJobPost(JobPost jobPost);

    List<JobPost> getAllJobPosts();

    JobPost updateJobPost(Long id, JobPost jobPost);
    JobPost getJobPostById(Long id);

    void deleteJobPost(Long id);
}
