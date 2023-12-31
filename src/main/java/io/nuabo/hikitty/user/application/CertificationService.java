package io.nuabo.hikitty.user.application;

import io.nuabo.common.domain.exception.MessageException;
import io.nuabo.hikitty.user.application.port.MailSender;
import io.nuabo.hikitty.user.application.port.MailSenderConfig;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    public void sendMailFromTemplate(String email, String certificationCode, String username, Long userId) {
        HashMap<String, String> values = setTemplateValue(username, certificationCode, userId);
        try {
            mailSender.sendMailFromTemplate(email, mailSenderConfig.getTitle(), values, mailSenderConfig.getTemplateName());
        } catch (MessagingException e) {
            throw new MessageException("Message Server", values.get("name"));
        }
    }
    private HashMap<String, String> setTemplateValue(String name, String certificationCode, Long userId) {
        HashMap<String, String> map = new HashMap<>();
        String certificationUrl = generateCertificationUrl(userId, certificationCode);
        map.put(mailSenderConfig.getTemplateValueName(), name);
        map.put(mailSenderConfig.getTemplateValueCertificationCode(), certificationUrl);

        return map;
    }
    private String generateCertificationUrl(long userId, String certificationCode) {
        return mailSenderConfig.getFirstUrl() + userId  + mailSenderConfig.getSecondUrl() + certificationCode;
    }


}
