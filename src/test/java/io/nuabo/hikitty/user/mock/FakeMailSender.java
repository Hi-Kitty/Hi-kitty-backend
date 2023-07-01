package io.nuabo.hikitty.user.mock;


import io.nuabo.hikitty.user.application.port.MailSender;
import jakarta.mail.MessagingException;

import java.util.HashMap;

public class FakeMailSender implements MailSender {

    public String email;
    public String title;
    public String content;

    @Override
    public void send(String email, String title, String content) {
        this.title = title;
        this.email = email;
        this.content = content;
    }

    @Override
    public void sendMailFromTemplate(String email, String title, HashMap<String, String> values, String templateName) throws MessagingException {
        this.title = title;
        this.email = email;
        this.content = values.get("name") + values.get("certificationCode");
    }
}
