package io.nuabo.hikitty.user.infrastructure;

import io.nuabo.hikitty.user.application.port.MailSenderConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
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
