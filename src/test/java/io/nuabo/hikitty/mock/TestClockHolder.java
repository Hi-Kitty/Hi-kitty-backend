package io.nuabo.hikitty.mock;

import io.nuabo.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
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
    public LocalDateTime currentDateNow() {
        return null;
    }

    @Override
    public Date expirationAccess() {
        return null;
    }

    @Override
    public long calculateDDay(LocalDateTime startDate, LocalDateTime endDate) {
        return 0;
    }

    @Override
    public String calculateProgressPercentage(long currentAmount, long totalAmount) {
        return null;
    }
}
