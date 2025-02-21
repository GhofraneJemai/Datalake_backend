package com.recruitment.datalake.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender emailSender;

    public String formatDateTime(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'à' hh:mm a");
        return localDateTime.format(formatter);
    }

    public void sendRecruitmentResult(String candidateEmail, String status, String recruitmentDate) {
        // Traduction du statut
        if ("APPROVED".equals(status)) {
            status = "Approuvé";
        } else if ("REJECTED".equals(status)) {
            status = "Rejeté";
        } else if ("PENDING".equals(status)) {
            status = "En attente";
        }

        String emailSubject = "Mise à jour du statut de votre candidature : " + status;
        String emailBody="";

        // Traitement du corps de l'email en fonction du statut
        if ("Approuvé".equals(status)) {
                String formattedDate = formatDateTime(recruitmentDate);
                emailBody = String.format(
                    "Cher(e) candidat(e),\n\n" +
                    "Nous avons le plaisir de vous informer que votre candidature a été %s. " +
                    "Nous vous félicitons pour cette réussite et sommes impatients de poursuivre le processus de recrutement avec vous.\n\n" +
                    "Votre date de recrutement est confirmée pour le : %s.\n\n" +
                    "N'hésitez pas à nous contacter si vous avez des questions ou avez besoin de plus d'informations. " +
                    "Nous sommes impatients de vous accueillir dans notre équipe.\n\n" +
                    "Cordialement,\n" +
                    "L'équipe de recrutement\n" +
                    "Datalake\n",
                    status, formattedDate
                );
            
        } else if ("Rejeté".equals(status)) {
            emailBody = String.format(
                "Cher(e) candidat(e),\n\n" +
                "Merci de l'intérêt que vous avez porté à Datalake. Nous avons le regret de vous informer que votre candidature a été %s. " +
                "Bien que nous ne puissions pas poursuivre avec votre candidature à ce moment, nous vous remercions sincèrement pour le temps et l'effort consacrés à votre candidature.\n\n" +
                "Nous vous souhaitons beaucoup de succès dans vos projets futurs et vous encourageons à postuler pour de futures opportunités chez nous.\n\n" +
                "Cordialement,\n" +
                "L'équipe de recrutement\n" +
                "Datalake\n",
                status
            );
        } else if ("En attente".equals(status)) {
            emailBody = String.format(
                "Cher(e) candidat(e),\n\n" +
                "Merci de l'intérêt que vous avez porté à Datalake. Nous tenons à vous informer que votre candidature est actuellement en %s. " +
                "Nous sommes encore en train d'examiner toutes les candidatures et reviendrons vers vous dès que possible avec plus d'informations.\n\n" +
                "Nous vous remercions de votre patience et de votre compréhension. Si vous avez des questions en attendant, n'hésitez pas à nous contacter.\n\n" +
                "Cordialement,\n" +
                "L'équipe de recrutement\n" +
                "Datalake\n",
                status
            );
        }

        // Envoi de l'email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(candidateEmail);
        message.setSubject(emailSubject);
        message.setText(emailBody);
        emailSender.send(message);
    }


}