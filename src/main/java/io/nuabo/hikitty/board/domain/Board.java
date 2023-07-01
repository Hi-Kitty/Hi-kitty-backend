package io.nuabo.hikitty.board.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Board {

    private final Long id;

    private final String title;

    private final String subTitle;

    private final String content;

    private final Long targetAmount;

    private final Long currentAmount;

    private final LocalDateTime endAt;

    private final LocalDateTime createdAt;

    @Builder
    public Board(Long id, String title, String subTitle, String content, Long targetAmount, Long currentAmount, LocalDateTime endAt, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.endAt = endAt;
        this.createdAt = createdAt;
    }
}
