package org.isp.emails;

public interface EmailService {
    void sendMessage(String to, String subject, String message);
}
