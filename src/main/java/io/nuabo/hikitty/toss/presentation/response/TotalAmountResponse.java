package io.nuabo.hikitty.toss.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TotalAmountResponse {
    private final int amountCount;
    private final int TotalAmount;

    @Builder
    public TotalAmountResponse(int amountCount, int totalAmount) {
        this.amountCount = amountCount;
        this.TotalAmount = totalAmount;
    }

    public static TotalAmountResponse from(int size, int sum) {
        return TotalAmountResponse.builder()
                .amountCount(size)
                .totalAmount(sum)
                .build();
    }
}
