package io.nuabo.hikitty.user.mock;

import io.nuabo.hikitty.user.application.port.MailSenderConfig;

public class FakeMailSenderConfig implements MailSenderConfig {

    private final String firstUrl;
    private final String secondUrl;
    private final String title;
    private final String content;

    public FakeMailSenderConfig(String firstUrl, String secondUrl, String title, String content) {
        this.firstUrl = firstUrl;
        this.secondUrl = secondUrl;
        this.title = title;
        this.content = content;
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
}
