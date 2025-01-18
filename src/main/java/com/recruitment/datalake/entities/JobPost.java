package com.recruitment.datalake.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private String requirements;

    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Application> applications;

    private LocalDateTime postedAt = LocalDateTime.now();

	public JobPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobPost(String title, String description, String location, String requirements,
			List<Application> applications, LocalDateTime postedAt) {
		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.requirements = requirements;
		this.applications = applications;
		this.postedAt = postedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}

	@Override
	public String toString() {
		return "JobPost [id=" + id + ", title=" + title + ", description=" + description + ", location=" + location
				+ ", requirements=" + requirements + ", applications=" + applications + ", postedAt=" + postedAt + "]";
	}

    // Getters and Setters
    
}
