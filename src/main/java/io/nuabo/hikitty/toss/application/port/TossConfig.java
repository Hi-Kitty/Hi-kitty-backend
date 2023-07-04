package io.nuabo.hikitty.toss.application.port;

public interface TossConfig {
    String getClientApiKey();

    String getClientSecretKey();

    String getSuccessUrl();

    String getFailUrl();
}
