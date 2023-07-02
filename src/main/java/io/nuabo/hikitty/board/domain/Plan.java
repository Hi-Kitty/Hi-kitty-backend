package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Plan {
    private final Long id;

    private final Board board;

    private final String reason;

    private final Long amount;

    private final LocalDateTime createdAt;


    @Builder
    public Plan(Long id, Board board, String reason, Long amount, LocalDateTime createdAt) {
        this.id = id;
        this.board = board;
        this.reason = reason;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static Plan from(Board board, PlanCreateRequest planCreateRequest) {
        return Plan.builder()
                .board(board)
                .reason(planCreateRequest.getReason())
                .amount(planCreateRequest.getAmount())
                .build();
    }
}
