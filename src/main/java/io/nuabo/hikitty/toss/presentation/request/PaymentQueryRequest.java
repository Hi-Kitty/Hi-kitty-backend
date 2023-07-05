package io.nuabo.hikitty.toss.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentQueryRequest {

    @Schema(description = "주문번호", example = "20210909123456789")
    @NotNull(message = "orderId is required")
    private final String orderId;

    @Schema(description = "결제키", example = "20210909123456789")
    @NotNull(message = "paymentKey is required")
    private final String paymentKey;

    @Schema(description = "결제금액", example = "1000")
    @NotNull(message = "amount is required")
    private final Long amount;

    @Builder
    public PaymentQueryRequest(String orderId, String paymentKey, Long amount) {
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.amount = amount;
    }
}
