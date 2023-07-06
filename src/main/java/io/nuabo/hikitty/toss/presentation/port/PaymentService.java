package io.nuabo.hikitty.toss.presentation.port;

import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.presentation.request.PaymentFailRequest;
import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;
import io.nuabo.hikitty.toss.presentation.request.OrderRequest;
import io.nuabo.hikitty.toss.presentation.response.CompleteResponse;
import io.nuabo.hikitty.toss.presentation.response.OrderResponse;
import io.nuabo.hikitty.toss.presentation.response.PaymentResponse;
import io.nuabo.hikitty.toss.presentation.response.TotalAmountResponse;

public interface PaymentService {
    OrderResponse request(OrderRequest request, String email);

    Payment process(PaymentQueryRequest request);

    PaymentResponse increaseBoard(Payment payment);

    void fail(PaymentFailRequest request);

    void increaseBoardNotResponse(Payment payment);

    CompleteResponse getByOrderId(String orderId);

    TotalAmountResponse getByEmail(String email);
}
