package io.nuabo.hikitty.board.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Plan {
    private final Long id;

    private final Board board;

    private final String reason;

    private final String amount;

    private final LocalDateTime createdAt;


    public Plan(Long id, Board board, String reason, String amount, LocalDateTime createdAt) {
        this.id = id;
        this.board = board;
        this.reason = reason;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}
