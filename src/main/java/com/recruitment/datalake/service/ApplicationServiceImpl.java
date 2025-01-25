package com.recruitment.datalake.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.repos.ApplicationRepository;
import com.recruitment.datalake.repos.CandidateRepository;
import com.recruitment.datalake.repos.JobPostRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobPostRepository jobPostRepository;
    private final EmailService emailService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, 
                                  CandidateRepository candidateRepository, 
                                  JobPostRepository jobPostRepository,EmailService emailService) {
        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobPostRepository = jobPostRepository;
        this.emailService = emailService;
    }

    @Override
    public Application applyForJob(Long candidateId, Long jobPostId, String coverLetter, String cvUrl) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        JobPost jobPost = null;
        if (jobPostId != null) {
            jobPost = jobPostRepository.findById(jobPostId)
                    .orElseThrow(() -> new RuntimeException("Job post not found"));
        }

        Application application = new Application();
        application.setCandidate(candidate);
        application.setJobPost(jobPost);
        application.setCoverLetter(coverLetter);
        application.setCvUrl(cvUrl);
        application.setStatus("PENDING");
        application.setCreatedAt(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
    public Application updateApplicationStatus(Long applicationId, String status, LocalDateTime recruitmentDate) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if ("APPROVED".equalsIgnoreCase(status)) {
            application.setRecruitmentDate(recruitmentDate);
        }
        application.setStatus(status);
        applicationRepository.save(application);

        // Send email notification to the candidate
        emailService.sendRecruitmentResult(application.getCandidate().getUser().getEmail(), status, recruitmentDate != null ? recruitmentDate.toString() : "");

        return application;
    }
    @Override
    public List<Application> getApplicationsByCandidateId(Long candidateId) {
        return applicationRepository.findByCandidate_Id(candidateId);
    }

    @Override
    public Map<JobPost, List<Application>> getApplicationsGroupedByJobPostWithCandidates() {
        List<Application> allApplications = applicationRepository.findAllApplicationsWithCandidateAndJobPost();
        return allApplications.stream()
                .collect(Collectors.groupingBy(Application::getJobPost));
    }
    @Override
    public List<Application> getAllApplicationsWithDetails() {
        return applicationRepository.findAllApplicationsWithCandidateAndJobPost();
    }

	@Override
	public Map<JobPost, List<Application>> getApplicationsGroupedByJobPost() {
		// TODO Auto-generated method stub
		return null;
	}

}
