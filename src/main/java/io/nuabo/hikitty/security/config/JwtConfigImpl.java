package io.nuabo.hikitty.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigImpl implements JwtConfig{

    @Value("${security.denied-uris}")
    private String[] deniedUris;

    @Value("${security.jwt.secret-key}")
    private String secret;


    @Override
    public String[] getDeniedUris() {
        return deniedUris;
    }

    @Override
    public String getSecret() {
        return secret;
    }
}
