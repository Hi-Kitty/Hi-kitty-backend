package io.nuabo.hikitty.user.application.port;

public interface MailSenderConfig {
    String getFirstUrl();

    String getSecondUrl();

    String getTitle();

    String getContent();
}
