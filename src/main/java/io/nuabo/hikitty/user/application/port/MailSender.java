package io.nuabo.hikitty.user.application.port;

public interface MailSender {
    void send(String email, String title, String content);
}
