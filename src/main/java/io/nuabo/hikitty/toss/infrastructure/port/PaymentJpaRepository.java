package io.nuabo.hikitty.toss.infrastructure.port;

import io.nuabo.hikitty.toss.infrastructure.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
