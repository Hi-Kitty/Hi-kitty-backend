package io.nuabo.hikitty.board.presentation.response;

import io.nuabo.hikitty.board.domain.Plan;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PlanResponse {
    private final Long id;

    private final String reason;

    private final Long amount;

    @Builder
    public PlanResponse(Long id, String reason, Long amount) {
        this.id = id;
        this.reason = reason;
        this.amount = amount;
    }

    public static List<PlanResponse> from(List<Plan> plans) {
        return plans.stream().map(plan -> PlanResponse.builder()
                .id(plan.getId())
                .reason(plan.getReason())
                .amount(plan.getAmount())
                .build()).toList();
    }
}
