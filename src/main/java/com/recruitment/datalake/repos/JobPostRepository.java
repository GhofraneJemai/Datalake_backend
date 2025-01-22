package com.recruitment.datalake.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.recruitment.datalake.entities.JobPost;
@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {}
