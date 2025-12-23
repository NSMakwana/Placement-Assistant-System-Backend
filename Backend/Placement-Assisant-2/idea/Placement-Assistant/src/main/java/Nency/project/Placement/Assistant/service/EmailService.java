package Nency.project.Placement.Assistant.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
