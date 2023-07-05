package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TossDto {

    private final String orderId;
    private final Long amount;

    @Builder
    public TossDto(String orderId, Long amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public static TossDto from(PaymentQueryRequest request) {
        return TossDto.builder()
            .orderId(request.getOrderId())
            .amount(request.getAmount())
            .build();
    }
}
