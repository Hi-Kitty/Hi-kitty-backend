package io.nuabo.common.application.port;


import java.time.LocalDateTime;
import java.util.Date;

public interface ClockHolder {
    long millis();

    Date now();

    LocalDateTime currentDateNow();

    Date expirationAccess();
    long calculateDDay(LocalDateTime startDate, LocalDateTime endDate);

    String calculateProgressPercentage(long currentAmount, long totalAmount);

    String hashedOrderId(Long userId, String name, Long amount);
}
