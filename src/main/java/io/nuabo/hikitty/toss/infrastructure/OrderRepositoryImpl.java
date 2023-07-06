package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.toss.application.port.OrderRepository;
import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import io.nuabo.hikitty.toss.infrastructure.entity.OrderEntity;
import io.nuabo.hikitty.toss.infrastructure.port.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.from(order)).toModel();
    }

    @Override
    public Optional<Order> findByOrderId(String orderId) {
        return orderJpaRepository.findByOrderId(orderId).map(OrderEntity::toModel);
    }

    @Override
    public Order getByOrderId(String orderId) {
        return findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundException("Order", orderId));
    }

    @Override
    public List<Order> findAllByUserIdAndPaymentStatus(Long userId, PaymentStatus status) {
        return orderJpaRepository.findAllByUserIdAndPaymentStatus(userId, status).stream().map(OrderEntity::toModel).toList();
    }
}
