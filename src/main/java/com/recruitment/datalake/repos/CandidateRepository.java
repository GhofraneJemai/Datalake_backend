package com.recruitment.datalake.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.datalake.entities.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	  	List<Candidate> findByFirstName(String firstName);
	    
	    
	    List<Candidate> findByLastName(String lastName);
	    
	    List<Candidate> findByEmail(String email);
	    
	    
}