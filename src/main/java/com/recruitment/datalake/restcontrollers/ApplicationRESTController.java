package com.recruitment.datalake.restcontrollers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.UrlResource;

import com.recruitment.datalake.entities.Application;
import com.recruitment.datalake.entities.JobPost;
import com.recruitment.datalake.service.ApplicationService;
@CrossOrigin(origins = "http://localhost:4200")
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

        System.out.println("Received application request:");
        System.out.println("Candidate ID: " + candidateId);
        System.out.println("Job Post ID: " + jobPostId);
        System.out.println("Cover Letter: " + coverLetter);
        System.out.println("CV File Name: " + cvFile.getOriginalFilename());

        try {
            String fileName = UUID.randomUUID().toString() + "_" + cvFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, cvFile.getBytes());

            applicationService.applyForJob(candidateId, jobPostId, coverLetter, filePath.toString());

            return ResponseEntity.ok("Application submitted successfully!");
        } catch (IOException e) {
            System.err.println("Error saving CV file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload CV.");
        }
    }

    // Endpoint to get all applications

    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String recruitmentDate) {

        LocalDateTime recruitmentDateTime = null;
        
        // Parse the recruitmentDate only if it's provided
        if (recruitmentDate != null && !recruitmentDate.isEmpty()) {
            recruitmentDateTime = LocalDateTime.parse(recruitmentDate);
        }

        Application updatedApplication = applicationService.updateApplicationStatus(id, status, recruitmentDateTime);
        return ResponseEntity.ok(updatedApplication);
    }

    @GetMapping("/by-candidate")
    public ResponseEntity<List<Application>> getApplicationsByCandidateId(@RequestParam Long candidateId) {
        List<Application> applications = applicationService.getApplicationsByCandidateId(candidateId);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/grouped-by-job-post")
    public ResponseEntity<Map<JobPost, List<Application>>> getApplicationsGroupedByJobPost() {
        try {
            List<Application> allApplications = applicationService.getAllApplications();

            if (allApplications.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }

            // Ensure all applications are grouped by a non-null JobPost
            Map<JobPost, List<Application>> groupedApplications = allApplications.stream()
                    .filter(application -> application.getJobPost() != null) // Filter out applications with null jobPost
                    .collect(Collectors.groupingBy(Application::getJobPost));

            if (groupedApplications.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }

            // Log for debugging purposes
            System.out.println("Grouped Applications: " + groupedApplications);

            return ResponseEntity.ok(groupedApplications);

        } catch (Exception e) {
            e.printStackTrace(); // Log error for further debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/all")
    public Map<JobPost, List<Application>> getApplicationsGroupedBy() {
        return applicationService.getApplicationsGroupedByJobPostWithCandidates();
    }
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplicationsWithDetails();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok("Application deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete application.");
        }
    }

    // ✅ Modifier les informations du candidat (coverLetter, cvUrl)
    @PutMapping("/update/{id}")
    public ResponseEntity<Application> updateCandidateInfo(
            @PathVariable Long id,
            @RequestParam String coverLetter,
            @RequestParam("cvFile") MultipartFile cvFile) {

        try {
            String fileName = UUID.randomUUID().toString() + "_" + cvFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, cvFile.getBytes());

            Application updatedApplication = applicationService.updateCandidateInfo(id, coverLetter, filePath.toString());
            return ResponseEntity.ok(updatedApplication);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




}
