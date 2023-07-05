package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.hikitty.toss.application.port.PaymentRepository;
import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.infrastructure.entity.PaymentEntity;
import io.nuabo.hikitty.toss.infrastructure.port.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;
    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.from(payment)).toModel();
    }
}
