package io.nuabo.common.application.port;

public interface UuidHolder {
    String randomNumber();

    String random();

    String encode(String key);
}
