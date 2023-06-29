package io.nuabo.common.application.port;


import java.util.Date;

public interface ClockHolder {
    long millis();

    Date now();

    Date expirationAccess();
}
