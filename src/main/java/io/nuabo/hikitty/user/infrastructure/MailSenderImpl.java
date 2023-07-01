package io.nuabo.hikitty.user.infrastructure;

import io.nuabo.hikitty.user.application.port.MailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Override
    public void send(String email, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendMailFromTemplate(String email, String title, HashMap<String, String> values, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(message, true);
            //메일 제목 설정
            helper.setSubject(title);

            //수신자 설정
            helper.setTo(email);
            //템플릿에 전달할 데이터 설정
            Context context = new Context();
            values.forEach((key, value)->{
                context.setVariable(key, value);
            });

            //메일 내용 설정 : 템플릿 프로세스
            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);

            //메일 보내기
            mailSender.send(message);

    }
}
