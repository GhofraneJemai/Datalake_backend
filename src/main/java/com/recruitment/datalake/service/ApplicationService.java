package com.recruitment.datalake.service;

import java.util.List;

import com.recruitment.datalake.entities.Application;

public interface ApplicationService {

    Application applyForJob(Long candidateId, Long jobPostId, String coverLetter, String cvUrl);
    List<Application> getAllApplications();
    Application getApplicationById(Long applicationId); 
}