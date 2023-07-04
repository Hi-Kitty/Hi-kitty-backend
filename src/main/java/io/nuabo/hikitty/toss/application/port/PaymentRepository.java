package io.nuabo.hikitty.toss.application.port;

import io.nuabo.hikitty.toss.domain.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findByOrderId(String orderId);

    Payment getByOrderId(String orderId);
}
