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
        // Analyser la chaîne d'entrée en un objet LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        
        // Définir le format souhaité
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'à' hh:mm a");
        
        // Formater la date et l'heure selon le format souhaité
        return localDateTime.format(formatter);
    }

    public void sendRecruitmentResult(String candidateEmail, String status, String recruitmentDate) {
        // Définir le sujet de l'email
        String emailSubject = "Mise à jour du statut de votre candidature : " + status;
        String emailBody;
        String formattedDate = formatDateTime(recruitmentDate);

        // Personnaliser le corps de l'email en fonction du statut de la candidature
        if ("APPROUVÉE".equalsIgnoreCase(status)) {
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
                status, formattedDate // Inclure la date de recrutement si applicable
            );
        } else {
            emailBody = String.format(
                "Cher(e) candidat(e),\n\n" +
                "Merci de l'intérêt que vous avez porté à [Nom de l'entreprise]. Nous avons le regret de vous informer que votre candidature a été %s. " +
                "Bien que nous ne puissions pas poursuivre avec votre candidature à ce moment, nous vous remercions sincèrement pour le temps et l'effort consacrés à votre candidature.\n\n" +
                "Nous vous souhaitons beaucoup de succès dans vos projets futurs et vous encourageons à postuler pour de futures opportunités chez nous.\n\n" +
                "Cordialement,\n" +
                "L'équipe de recrutement\n" +
                "Datalake\n",
                status
            );
        }

        // Créer et envoyer l'email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(candidateEmail);
        message.setSubject(emailSubject);
        message.setText(emailBody);

        // Envoyer l'email
        emailSender.send(message);
    }
}
