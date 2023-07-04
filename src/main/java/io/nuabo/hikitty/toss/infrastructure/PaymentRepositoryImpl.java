package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.toss.application.port.PaymentRepository;
import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.infrastructure.port.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.from(payment)).toModel();
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        return paymentJpaRepository.findByOrderId(orderId).map(PaymentEntity::toModel);
    }

    @Override
    public Payment getByOrderId(String orderId) {
        return findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundException("Payment", orderId));
    }
}
