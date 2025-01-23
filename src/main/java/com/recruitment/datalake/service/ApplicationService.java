package com.recruitment.datalake.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.JobPost;

public interface ApplicationService {

    Application applyForJob(Long candidateId, Long jobPostId, String coverLetter, String cvUrl);
    List<Application> getAllApplications();
    Application getApplicationById(Long applicationId);
    Application updateApplicationStatus(Long applicationId, String status, LocalDateTime recruitmentDate);
    List<Application> getApplicationsByCandidateId(Long candidateId);
    Map<JobPost, List<Application>> getApplicationsGroupedByJobPost();
	Map<JobPost, List<Application>> getApplicationsGroupedByJobPostWithCandidates();
	List<Application> getAllApplicationsWithDetails();

}