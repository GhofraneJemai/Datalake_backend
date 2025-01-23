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

    public Candidate registerCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

	@Override
	public Candidate saveCandidate(Candidate candidate) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Candidate> findByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Candidate> findByCompany(String company) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Candidate getCandidateWithApplications(Long id) {
        return candidateRepository.findCandidateWithApplications(id);
    }
}
