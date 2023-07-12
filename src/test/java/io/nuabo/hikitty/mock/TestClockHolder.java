package io.nuabo.hikitty.mock;

import io.nuabo.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public Long calculateProgressPercentage(long currentAmount, long totalAmount) {
        double percentage = (double) currentAmount / totalAmount * 100;
        return Math.round(Math.min(100, percentage)); // 100%를 초과하지 않도록 보정, 반올림
    }

    @Override
    public String hashedOrderId(Long userId, String name, Long amount) {
        return null;
    }
}
