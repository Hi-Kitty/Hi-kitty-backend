package io.nuabo.common.infrastructure;

import io.nuabo.common.application.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;
// 수정
@Component
public class SystemClockHolder implements ClockHolder {

//    @Value("${security.jwt.expiration-time-access}")
//    private long expirationAccess;

    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }

    @Override
    public Date now() {
        return new Date(millis());
    }

    @Override
    public Date expirationAccess() {
        return new Date(millis() + 1000 * 60 * 60 * 24 * 7);
//        return new Date(millis() + expirationAccess);
    }
}
