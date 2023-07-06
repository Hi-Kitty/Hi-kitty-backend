package io.nuabo.hikitty.toss.infrastructure.port;

import io.nuabo.hikitty.toss.domain.PaymentStatus;
import io.nuabo.hikitty.toss.infrastructure.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(String orderId);

    List<OrderEntity> findAllByUserIdAndPaymentStatus(Long userId, PaymentStatus paymentStatus);

    Page<OrderEntity> findAllByUserIdAndPaymentStatus(Long userId, PaymentStatus paymentStatus, Pageable pageable);
}
