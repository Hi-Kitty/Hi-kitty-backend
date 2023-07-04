package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.hikitty.toss.application.port.TossConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TossConfigImpl implements TossConfig {

    @Value("${payment.toss.client-api-key}")
    private String clientApiKey;

    @Value("${payment.toss.client-secret-key}")
    private String clientSecretKey;

    @Value("${payment.toss.success-url}")
    private String successUrl;

    @Value("${payment.toss.fail-url}")
    private String failUrl;

    @Override
    public String getClientApiKey() {
        return clientApiKey;
    }

    @Override
    public String getClientSecretKey() {
        return clientSecretKey;
    }

    @Override
    public String getSuccessUrl() {
        return successUrl;
    }

    @Override
    public String getFailUrl() {
        return failUrl;
    }
}
