package io.nuabo.hikitty.board.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class Fundraiser {

    private final Long id;

    private final Board board;

    private final Long fundraiserId;

    private final String name;

    private final String profileName;

    private final String profileUrl;

    private final LocalDateTime createdAt;

    @Builder
    public Fundraiser(Long id, Board board, Long fundraiserId, String name, String profileName, String profileUrl, LocalDateTime createdAt) {
        this.id = id;
        this.board = board;
        this.fundraiserId = fundraiserId;
        this.name = name;
        this.profileName = profileName;
        this.profileUrl = profileUrl;
        this.createdAt = createdAt;
    }
}
