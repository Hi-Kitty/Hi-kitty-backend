package io.nuabo.hikitty.toss.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentFailRequest {

    @Schema(description = "에러코드", example = "PAS_PROCESS_CANCLED")
    private final String errorCode;

    @Schema(description = "에러메시지", example = "결제가 취소되었습니다.")
    private final String errorMsg;

    @Schema(description = "주문번호", example = "20210909123456789")
    private final String orderId;

    @Builder
    public PaymentFailRequest(String errorCode, String errorMsg, String orderId) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.orderId = orderId;
    }
}
