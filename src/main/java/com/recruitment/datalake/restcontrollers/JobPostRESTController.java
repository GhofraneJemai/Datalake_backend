package com.recruitment.datalake.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.service.JobPostService;

@RestController
@RequestMapping("/api/jobposts")
public class JobPostRESTController {

    @Autowired
    private JobPostService jobPostService;

    @PostMapping("/create")
    public ResponseEntity<JobPost> createJobPost(@RequestBody JobPost jobPost) {
        return ResponseEntity.ok(jobPostService.createJobPost(jobPost));
    }

    @GetMapping
    public ResponseEntity<List<JobPost>> getAllJobPosts() {
        return ResponseEntity.ok(jobPostService.getAllJobPosts());
    }
}

