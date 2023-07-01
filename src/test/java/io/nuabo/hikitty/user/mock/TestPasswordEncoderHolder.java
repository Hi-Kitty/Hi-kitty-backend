package io.nuabo.hikitty.user.mock;

import io.nuabo.hikitty.security.application.port.PasswordEncoderHolder;

public class TestPasswordEncoderHolder implements PasswordEncoderHolder {
    @Override
    public String encode(String password) {
        return password;
    }
}
