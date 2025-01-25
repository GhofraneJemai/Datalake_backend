package com.recruitment.datalake.entities;


import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Candidate {

	@Id
    private Long id; // Same as User ID

    @OneToOne
    @MapsId
    private User user;


    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @JsonBackReference 
    private List<Application> applications;

	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Candidate(User user) {
		super();
		this.user = user;
	}
	public Candidate(User user, List<Application> applications) {
		super();
		this.user = user;
		this.applications = applications;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	@Override
	public String toString() {
		return "Candidate [id=" + id + ", user=" + user + ", applications=" + applications + "]";
	}

	
  
}
