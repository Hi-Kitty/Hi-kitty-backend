package io.nuabo.hikitty.toss.application.port;

import io.nuabo.hikitty.toss.domain.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}
