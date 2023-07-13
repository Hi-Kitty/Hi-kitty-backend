package io.nuabo.hikitty.board.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlanCreateRequest {

    @NotNull(message = "reason cannot be null")
    @Size(min=1, max=255, message = "최소한의 이유를 적어주세요.")
    @Schema(description = "reason",  example = "최소한의 이유를 적어주세요. - 테스트시 []를 추가하세요.")
    private final String reason;

    @NotNull(message = "amount cannot be null")
    @Size(min=1, max=255, message = "금액을 입력해주세요.")
    @Schema(description = "amount", example = "1000")
    private final Long amount;

    @Builder
    public PlanCreateRequest(
            @JsonProperty("reason") String reason,
            @JsonProperty("amount") Long amount) {
        this.reason = reason;
        this.amount = amount;
    }
}
