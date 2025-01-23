package com.recruitment.datalake.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.service.JobPostService;
@CrossOrigin(origins = "http://localhost:4200")
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
    @PutMapping("/{id}")
    public ResponseEntity<JobPost> updateJobPost(@PathVariable Long id, @RequestBody JobPost jobPost) {
        return ResponseEntity.ok(jobPostService.updateJobPost(id, jobPost));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPost(@PathVariable Long id) {
        jobPostService.deleteJobPost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobPost> getJobPostById(@PathVariable Long id) {
        JobPost jobPost = jobPostService.getJobPostById(id);
        if (jobPost != null) {
            return ResponseEntity.ok(jobPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/jobposts/{id}")
    public ResponseEntity<JobPost> getJobPost(@PathVariable Long id) {
        JobPost jobPost = jobPostService.getJobPostWithApplications(id);
        return ResponseEntity.ok(jobPost);
    }

}

