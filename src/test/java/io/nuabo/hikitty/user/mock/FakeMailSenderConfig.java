package io.nuabo.hikitty.user.mock;

import io.nuabo.hikitty.user.application.port.MailSenderConfig;

public class FakeMailSenderConfig implements MailSenderConfig {

    private final String firstUrl;
    private final String secondUrl;
    private final String title;
    private final String content;

    private final String templateName;
    private final String templateValueName;
    private final String templateValueCertificationCode;


    public FakeMailSenderConfig(String firstUrl, String secondUrl, String title, String content, String templateName, String templateValueName, String templateValueCertificationCode) {
        this.firstUrl = firstUrl;
        this.secondUrl = secondUrl;
        this.title = title;
        this.content = content;
        this.templateName = templateName;
        this.templateValueName = templateValueName;
        this.templateValueCertificationCode = templateValueCertificationCode;
    }


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
        return templateName;
    }

    @Override
    public String getTemplateValueName() {
        return templateValueName;
    }

    @Override
    public String getTemplateValueCertificationCode() {
        return templateValueCertificationCode;
    }
}
