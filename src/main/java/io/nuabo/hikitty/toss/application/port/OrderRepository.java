package io.nuabo.hikitty.toss.application.port;

import io.nuabo.hikitty.toss.domain.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByOrderId(String orderId);

    Order getByOrderId(String orderId);
}
