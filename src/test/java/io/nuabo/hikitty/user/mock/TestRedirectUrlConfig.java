package io.nuabo.hikitty.user.mock;

import io.nuabo.common.presentation.port.RedirectUrlConfig;

public class TestRedirectUrlConfig implements RedirectUrlConfig {

    private final String userSave;


    public TestRedirectUrlConfig(String userSave) {
        this.userSave = userSave;
    }

    @Override
    public String getUserSave() {
        return userSave;
    }

    @Override
    public String getPayment() {
        return null;
    }

    @Override
    public String getFail() {
        return null;
    }
}
