package io.nuabo.hikitty.security.config;

public interface JwtConfig {

    String[] getDeniedUris();

    String[] getPermitUrisByFundraiser();
    String getSecret();

    String[] getPermitUrisByDoner();
}
