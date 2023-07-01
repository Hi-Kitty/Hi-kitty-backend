package io.nuabo.hikitty.security.config;

public interface JwtConfig {

    String[] getDeniedUris();

    String getSecret();
}
