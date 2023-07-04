package io.nuabo.hikitty.toss.infrastructure.port;

import io.nuabo.hikitty.toss.infrastructure.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByOrderId(String orderId);
}
