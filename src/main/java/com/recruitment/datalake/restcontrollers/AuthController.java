package com.recruitment.datalake.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.datalake.dto.LoginRequest;
import com.recruitment.datalake.dto.RegisterCandidateRequest;
import com.recruitment.datalake.service.AuthService;
import com.recruitment.datalake.service.RegistrationService;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCandidate(@RequestBody RegisterCandidateRequest request) {
        registrationService.registerCandidate(request);
        return ResponseEntity.ok("Candidate registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
