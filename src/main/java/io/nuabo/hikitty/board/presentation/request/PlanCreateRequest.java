package io.nuabo.hikitty.board.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PlanCreateRequest {

    @NotNull(message = "reason cannot be null")
    @Schema(description = "reason",  example = "최소한의 이유를 적어주세요. - 테스트시 []를 추가하세요.")
    private final String reason;

    @NotNull(message = "amount cannot be null")
    @Schema(description = "amount", example = "1000")
    private final Long amount;

    public PlanCreateRequest(
            @JsonProperty("reason") String reason,
            @JsonProperty("amount") Long amount) {
        this.reason = reason;
        this.amount = amount;
    }
}
