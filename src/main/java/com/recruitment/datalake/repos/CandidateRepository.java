package com.recruitment.datalake.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recruitment.datalake.entities.Candidate;
@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	@Query("SELECT c FROM Candidate c JOIN FETCH c.applications WHERE c.id = :id")
	Candidate findCandidateWithApplications(@Param("id") Long id);

	  	List<Candidate> findByFirstName(String firstName);
	    
	    
	    List<Candidate> findByLastName(String lastName);
	    
	    List<Candidate> findByEmail(String email);
	    
	    
}