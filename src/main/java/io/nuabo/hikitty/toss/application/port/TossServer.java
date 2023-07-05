package io.nuabo.hikitty.toss.application.port;

import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;

public interface TossServer {
    PaymentResHandleDto send(PaymentQueryRequest request, Order order);
}
