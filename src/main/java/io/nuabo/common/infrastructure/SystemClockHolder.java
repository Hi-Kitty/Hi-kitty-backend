package io.nuabo.common.infrastructure;

import io.nuabo.common.application.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Component
public class SystemClockHolder implements ClockHolder {


    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }

    @Override
    public Date now() {
        return new Date(millis());
    }

    @Override
    public LocalDateTime currentDateNow() {
        return LocalDateTime.now();
    }


    @Override
    public Date expirationAccess() {
        return new Date(millis() + 1000 * 60 * 60 * 24 * 7);
    }
    @Override
    public long calculateDDay(LocalDateTime startDate, LocalDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public Long calculateProgressPercentage(long currentAmount, long totalAmount) {
        // 진행 기간의 퍼센트 계산
        double percentage = (double) currentAmount / totalAmount * 100;


        return Math.round(Math.min(100, percentage)); // 100%를 초과하지 않도록 보정, 반올림
    }

    @Override
    public String hashedOrderId(Long userId, String name, Long amount) {
        return String.valueOf(Objects.hash(userId, name, amount, millis()));
    }
}
