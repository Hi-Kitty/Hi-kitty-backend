package io.nuabo.hikitty.toss.presentation.response;

import io.nuabo.hikitty.board.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class BoardYearMonthlyAmounts {
    private final Long boardId;
    private final LocalDateTime createdAt;
    private final LocalDateTime endAt;
    private final Map<String, Integer> yearMonthlyAmounts;
    private final Long totalAmount;

    @Builder
    public BoardYearMonthlyAmounts(Long boardId, LocalDateTime createdAt, LocalDateTime endAt,  Map<String, Integer> yearMonthlyAmounts, Long totalAmount) {
        this.boardId = boardId;
        this.createdAt = createdAt;
        this.endAt = endAt;
        this.yearMonthlyAmounts = yearMonthlyAmounts;
        this.totalAmount = totalAmount;
    }

    public static BoardYearMonthlyAmounts from(Board board, TotalYearMonthlyAmounts totalYearMonthlyAmounts) {
        return BoardYearMonthlyAmounts.builder()
                .boardId(board.getId())
                .createdAt(board.getCreatedAt())
                .endAt(board.getEndAt())
                .yearMonthlyAmounts(totalYearMonthlyAmounts.getMonthlyAmounts())
                .totalAmount(totalYearMonthlyAmounts.getTotalAmount())
                .build();
    }
}
