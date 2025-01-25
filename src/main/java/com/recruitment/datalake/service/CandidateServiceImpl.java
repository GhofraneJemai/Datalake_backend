package com.recruitment.datalake.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruitment.datalake.entities.Candidate;
import com.recruitment.datalake.repos.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {


    @Autowired
    private CandidateRepository candidateRepository;
    

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

	@Override
	public Candidate saveCandidate(Candidate candidate) {
		return candidateRepository.save(candidate);
	}

	@Override
	public Candidate getCandidateById(Long id) {
		return candidateRepository.findById(id).orElse(null);
	}

	@Override
	public Candidate updateCandidate(Candidate candidate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCandidate(Long id) {
	    // Find the candidate by id
	    Candidate candidate = candidateRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Candidate not found"));
	    
	    // Delete the candidate
	    candidateRepository.delete(candidate);
	}

	@Override
	public List<Candidate> findByFirstName(String firstName) {
	    // Find candidates by firstName from the User entity
	    return candidateRepository.findByUserFirstName(firstName); // Ensure repository has this method
	}

	@Override
	public Candidate getCandidateWithApplications(Long id) {
        return candidateRepository.findCandidateWithApplications(id);
    }
	public List<Candidate> findCandidatesByFirstName(String firstName) {
        return candidateRepository.findByUserFirstName(firstName);
    }

	@Override
	public List<Candidate> findByCompany(String company) {
		// TODO Auto-generated method stub
		return null;
	}
}
