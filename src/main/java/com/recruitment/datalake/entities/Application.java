package com.recruitment.datalake.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    @JsonBackReference // Prevent circular reference while serializing
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_post_id", nullable = true)
    private JobPost jobPost;

    private String coverLetter;
    private String cvUrl; // CV file path
    private String status = "PENDING";

    private LocalDateTime createdAt = LocalDateTime.now();
	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Application(Candidate candidate, JobPost jobPost, String coverLetter, String cvUrl, String status,
			LocalDateTime createdAt) {
		super();
		this.candidate = candidate;
		this.jobPost = jobPost;
		this.coverLetter = coverLetter;
		this.cvUrl = cvUrl;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public JobPost getJobPost() {
		return jobPost;
	}

	public void setJobPost(JobPost jobPost) {
		this.jobPost = jobPost;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getCvUrl() {
		return cvUrl;
	}

	public void setCvUrl(String cvUrl) {
		this.cvUrl = cvUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", candidate=" + candidate + ", jobPost=" + jobPost + ", coverLetter="
				+ coverLetter + ", cvUrl=" + cvUrl + ", status=" + status + ", createdAt=" + createdAt + "]";
	}



    // Getters and Setters
}

