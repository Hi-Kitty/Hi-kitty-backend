package io.nuabo.hikitty.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigImpl implements JwtConfig{

    @Value("${security.denied-uris}")
    private String[] deniedUris;

    @Value("${security.jwt.secret-key}")
    private String secret;

    @Value("${security.premit-uris-by-fundraiser}")
    private String[] permitUrisByFundraiser;

    @Override
    public String[] getDeniedUris() {
        return deniedUris;
    }

    @Override
    public String[] getPermitUrisByFundraiser() {
        return permitUrisByFundraiser;
    }

    @Override
    public String getSecret() {
        return secret;
    }
}
