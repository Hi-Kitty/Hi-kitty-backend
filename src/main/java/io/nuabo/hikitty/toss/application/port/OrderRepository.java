package io.nuabo.hikitty.toss.application.port;

import io.nuabo.hikitty.board.presentation.request.PageNationRequest;
import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByOrderId(String orderId);

    Order getByOrderId(String orderId);

    List<Order> findAllByUserIdAndPaymentStatus(Long userId, PaymentStatus status);

    Page<Order> findPageAllByUserIdAndPaymentStatus(Long id, PaymentStatus paid, PageNationRequest pageNationRequest);

    List<Order> findAllByBoardIdAndPaymentStatus(Long boardId, PaymentStatus paid);
}
