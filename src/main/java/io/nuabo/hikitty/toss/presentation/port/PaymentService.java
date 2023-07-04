package io.nuabo.hikitty.toss.presentation.port;

import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;
import io.nuabo.hikitty.toss.presentation.request.PaymentRequest;
import io.nuabo.hikitty.toss.presentation.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse request(PaymentRequest request, String email);

    void verify(PaymentQueryRequest request);
}
