package io.nuabo.hikitty.user.application;

import io.nuabo.hikitty.user.application.port.MailSender;
import io.nuabo.hikitty.user.application.port.MailSenderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final MailSender mailSender;
    private final MailSenderConfig mailSenderConfig;


    public void send(String email, Long userId, String certificationCode) {
        String certificationUrl = generateCertificationUrl(userId, certificationCode);
        String result = mailSenderConfig.getContent() + certificationUrl;
        mailSender.send(email, mailSenderConfig.getTitle(), result);
    }

    public String generateCertificationUrl(long userId, String certificationCode) {
        return mailSenderConfig.getFirstUrl() + userId + mailSenderConfig.getSecondUrl() + certificationCode;
    }


}
