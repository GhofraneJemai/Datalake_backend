package com.recruitment.datalake.restcontrollers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationRESTController {

    @Autowired
    private ApplicationService applicationService;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(
            @RequestParam("candidateId") Long candidateId,
            @RequestParam(value = "jobPostId", required = false) Long jobPostId,
            @RequestParam("coverLetter") String coverLetter,
            @RequestParam("cvFile") MultipartFile cvFile) {

        try {
            String fileName = UUID.randomUUID().toString() + "_" + cvFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, cvFile.getBytes());

            applicationService.applyForJob(candidateId, jobPostId, coverLetter, filePath.toString());

            return ResponseEntity.ok("Application submitted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload CV.");
        }
    }

    // Endpoint to get all applications
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam String recruitmentDate) {
        LocalDateTime recruitmentDateTime = LocalDateTime.parse(recruitmentDate);
        Application updatedApplication = applicationService.updateApplicationStatus(id, status, recruitmentDateTime);
        return ResponseEntity.ok(updatedApplication);
    }

}
