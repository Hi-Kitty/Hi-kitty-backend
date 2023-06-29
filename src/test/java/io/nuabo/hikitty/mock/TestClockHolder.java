package io.nuabo.hikitty.mock;

import io.nuabo.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long millis;
    @Override
    public long millis() {
        return millis;
    }

    @Override
    public Date now() {
        return null;
    }

    @Override
    public Date expirationAccess() {
        return null;
    }
}
