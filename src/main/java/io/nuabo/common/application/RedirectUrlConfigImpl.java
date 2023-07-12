package io.nuabo.common.application;

import io.nuabo.common.presentation.port.RedirectUrlConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedirectUrlConfigImpl implements RedirectUrlConfig {

    @Value("${redirect.user-save-url}")
    private String userSave;

    @Value("${redirect.payment-url}")
    private String payment;

    @Value("${redirect.fail-url}")
    private String fail;
    @Override
    public String getUserSave() {
        return userSave;
    }
    @Override
    public String getPayment() {
        return payment;
    }

    @Override
    public String getFail() {
        return fail;
    }

}