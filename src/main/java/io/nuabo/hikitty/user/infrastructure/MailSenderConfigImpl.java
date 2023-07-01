package io.nuabo.hikitty.user.infrastructure;

import io.nuabo.hikitty.user.application.port.MailSenderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSenderConfigImpl implements MailSenderConfig {
    @Value("${mail.first-url}")
    private String firstUrl;

    @Value("${mail.second-url}")
    private String secondUrl;

    @Value("${mail.title}")
    private String title;

    @Value("${mail.content}")
    private String content;
//
//    @Value("${mail.template-name}")
//    private String templateName;
//
//    @Value("${mail.template-value-name}")
//    private String templateValueName;
//
//    @Value("${mail.template-value-certification-code}")
//    private String templateValueCertificationCode;


    @Override
    public String getFirstUrl() {
        return firstUrl;
    }

    @Override
    public String getSecondUrl() {
        return secondUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getTemplateName() {
        return "welcome";
    }

    @Override
    public String getTemplateValueName() {
        return "name";
    }

    @Override
    public String getTemplateValueCertificationCode() {
        return "certificationCode";
    }
}
