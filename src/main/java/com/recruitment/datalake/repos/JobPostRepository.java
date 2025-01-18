package com.recruitment.datalake.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.datalake.entities.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {}
