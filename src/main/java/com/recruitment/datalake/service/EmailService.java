package com.recruitment.datalake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender emailSender;

    public void sendRecruitmentResult(String candidateEmail, String status, String recruitmentDate) {
    	// Sujet de l'email
        String emailSubject = "Your Application Status - " + status;
        String emailBody;

        // Vérification du statut de la candidature pour ajouter la date de recrutement si accepté
        if ("APPROVED".equalsIgnoreCase(status)) {
            emailBody = String.format(
                "Dear Candidate,\n\nWe are pleased to inform you that your application has been %s.\n" +
                "Your recruitment date is: %s.\n\nBest regards,\nThe Recruitment Team",
                status, recruitmentDate  // Format de la date de recrutement
            );
        } else {
            emailBody = String.format(
                "Dear Candidate,\n\nWe regret to inform you that your application has been %s.\n\n" +
                "Best regards,\nThe Recruitment Team",
                status
            );
        }

        // Création du message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(candidateEmail);
        message.setSubject(emailSubject);
        message.setText(emailBody);

        // Envoi de l'email
        emailSender.send(message);
    }

}
