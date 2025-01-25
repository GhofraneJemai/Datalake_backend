package com.recruitment.datalake.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.datalake.dto.LoginRequest;
import com.recruitment.datalake.dto.RegisterCandidateRequest;
import com.recruitment.datalake.service.AuthService;
import com.recruitment.datalake.service.RegistrationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCandidate(@RequestBody RegisterCandidateRequest request) {
        try {
        	registrationService.registerCandidate(request);
            return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "text/plain")  // Explicitly set content type to text
                    .body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "text/plain")  // Explicitly set content type to text
                    .body("Registration failed");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            return ResponseEntity.ok()
                                 .header("Content-Type", "text/plain") // Ensure correct header
                                 .body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .header("Content-Type", "text/plain")
                                 .body("Invalid credentials");
        }
    }

}
