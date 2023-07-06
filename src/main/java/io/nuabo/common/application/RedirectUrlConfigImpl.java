package io.nuabo.common.application;

import io.nuabo.common.presentation.port.RedirectUrlConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedirectUrlConfigImpl implements RedirectUrlConfig {

    @Value("${redirect.user-save}")
    private String userSave;

    @Override
    public String userSave() {
        return userSave;
    }
}