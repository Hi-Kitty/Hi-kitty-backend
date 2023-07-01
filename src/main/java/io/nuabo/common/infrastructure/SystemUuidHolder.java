package io.nuabo.common.infrastructure;

import io.nuabo.common.application.port.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {
    @Override
    public String random() {
        String randomUUIDString = UUID.randomUUID().toString().replaceAll("-", "");
        String randomNumberString = randomUUIDString.substring(0, 8);

        long randomNumber = Long.parseLong(randomNumberString, 16);
        String formattedNumber = String.format("%08d", randomNumber);
        return formattedNumber;
    }
}
