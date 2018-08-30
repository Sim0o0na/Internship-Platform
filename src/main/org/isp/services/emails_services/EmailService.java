package org.isp.services.emails_services;

public interface EmailService {
    void sendMessage(String to, String subject, String message);
}
