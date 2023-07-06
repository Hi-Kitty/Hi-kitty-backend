package io.nuabo.hikitty.toss.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentQueryRequest {

    @Schema(description = "주문번호", example = "20210909123456789")
    private final String orderId;

    @Schema(description = "결제키", example = "20210909123456789")
    private final String paymentKey;

    @Schema(description = "결제금액", example = "1000")
    private final Long amount;

    @Builder
    public PaymentQueryRequest(String orderId, String paymentKey, Long amount) {
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.amount = amount;
    }
}
