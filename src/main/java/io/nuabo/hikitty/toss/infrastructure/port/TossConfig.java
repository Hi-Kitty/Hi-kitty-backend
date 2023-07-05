package io.nuabo.hikitty.toss.infrastructure.port;

public interface TossConfig {
    String getClientApiKey();

    String getClientSecretKey();

    String getSuccessUrl();

    String getFailUrl();

    String getTossOriginUrl();
}
