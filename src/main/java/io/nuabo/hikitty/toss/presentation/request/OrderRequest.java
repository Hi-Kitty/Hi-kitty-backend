package io.nuabo.hikitty.toss.presentation.request;

import io.nuabo.hikitty.toss.domain.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequest {

    @NotNull(message = "amount cannot be null")
    @Schema(description = "결제 금액",  example = "1000")
    private final Long amount;


    @NotNull(message = "boardId cannot be null")
    @Schema(description = "게시판 id", example = "1")
    private final Long boardId;

    @NotNull(message = "payType cannot be null")
    @Schema(description = "결제 타입",  example = "CARD, PHONE, VBANK 중 하나 ")
    private final PayType payType;


    @Builder
    public OrderRequest(
            PayType payType,
            Long amount,
            Long boardId) {
        this.payType = payType;
        this.amount = amount;
        this.boardId = boardId;
    }
}
