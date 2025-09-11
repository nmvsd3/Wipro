package com.playstore.notificationservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.playstore.notificationservice.entity.Notification;
import com.playstore.notificationservice.repository.NotificationRepository;

import jakarta.mail.internet.MimeMessage;

// Marks this class as a service component
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final NotificationRepository repo;

    // Constructor injection for dependencies
    public EmailService(JavaMailSender mailSender, NotificationRepository repo) {
        this.mailSender = mailSender;
        this.repo = repo;
    }

    // Method to send an email and store it in the database
    public void sendEmail(String to, String subject, String body) {
        try {
            // Save notification details in DB
            Notification notification = Notification.builder()
                    .recipient(to)
                    .subject(subject)
                    .body(body)
                    .createdAt(LocalDateTime.now())
                    .build();
            repo.save(notification);

            // Prepare and send email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true → body supports HTML

            mailSender.send(message);

        } catch (Exception e) {
            // Handle any failure during email sending
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    // Method to fetch all notifications for a specific user
    public List<Notification> getUserNotifications(String email) {
        return repo.findByRecipient(email);
    }
}
