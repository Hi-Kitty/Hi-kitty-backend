package io.nuabo.hikitty.board.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Heart {

    private final Long id;

    private final Board board;

    private final Status status;

    private final LocalDateTime createdAt;

    @Builder
    public Heart(Long id, Board board, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.board = board;
        this.status = status;
        this.createdAt = createdAt;
    }
}
