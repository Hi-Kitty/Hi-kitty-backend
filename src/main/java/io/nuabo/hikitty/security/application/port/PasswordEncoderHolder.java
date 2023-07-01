package io.nuabo.hikitty.security.application.port;

public interface PasswordEncoderHolder {
    String encode(String password);
}
