package com.recruitment.datalake.service;

import java.util.List;

import com.recruitment.datalake.entities.Candidate;

public interface CandidateService {

    Candidate saveCandidate(Candidate candidate);

    Candidate getCandidateById(Long id);

    List<Candidate> getAllCandidates();

    Candidate updateCandidate(Candidate candidate);

    void deleteCandidate(Long id);

    List<Candidate> findByFirstName(String firstName);

    List<Candidate> findByCompany(String company);

	Candidate getCandidateWithApplications(Long id);
}
