package io.nuabo.hikitty.user.application.port;

import jakarta.mail.MessagingException;

import java.util.HashMap;

public interface MailSender {
    void send(String email, String title, String content);

    void sendMailFromTemplate(String email, String title, HashMap<String, String> values, String templateName) throws MessagingException;
}
