package com.recruitment.datalake.repos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recruitment.datalake.entities.Application;
@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCandidate_Id(Long candidateId);
    @Query("SELECT a FROM Application a JOIN FETCH a.candidate JOIN FETCH a.jobPost WHERE a.id = :id")
    Application findApplicationWithCandidateAndJobPost(@Param("id") Long id);
    @Query("SELECT a FROM Application a JOIN FETCH a.candidate JOIN FETCH a.jobPost")
    List<Application> findAllApplicationsWithCandidateAndJobPost();

}
