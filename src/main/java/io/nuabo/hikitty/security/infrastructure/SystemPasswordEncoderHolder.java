package io.nuabo.hikitty.security.infrastructure;

import io.nuabo.hikitty.security.application.port.PasswordEncoderHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemPasswordEncoderHolder implements PasswordEncoderHolder {

    private final PasswordEncoder passwordEncoder;

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
