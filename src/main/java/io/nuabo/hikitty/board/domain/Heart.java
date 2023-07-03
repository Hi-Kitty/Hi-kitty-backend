package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Heart {

    private final Long id;

    private final Board board;

    private final Status status;

    private final LocalDateTime createdAt;

    private final Long donerId;

    private final String donerName;

    private final String donerProfileName;

    private final String donerProfileUrl;
    @Builder
    public Heart(Long id, Board board, Status status, LocalDateTime createdAt, Long donerId, String donerName, String donerProfileName, String donerProfileUrl) {
        this.id = id;
        this.board = board;
        this.status = status;
        this.createdAt = createdAt;
        this.donerId = donerId;
        this.donerName = donerName;
        this.donerProfileName = donerProfileName;
        this.donerProfileUrl = donerProfileUrl;
    }

    public static Heart from(Board board, User user, Profile profile) {
        return Heart.builder()
                .board(board)
                .status(Status.INACTIVE)
                .donerId(user.getId())
                .donerName(user.getName())
                .donerProfileName(profile.getOriginalName())
                .donerProfileUrl(profile.getUrl())
                .build();
    }

    public static Heart from(Board board, User user) {
        return Heart.builder()
                .board(board)
                .status(Status.INACTIVE)
                .donerId(user.getId())
                .donerName(user.getName())
                .build();
    }

    public Heart inActivce() {
        return Heart.builder(
        ).id(id)
        .board(board)
        .status(Status.INACTIVE)
        .donerId(donerId)
        .donerName(donerName)
        .donerProfileName(donerProfileName)
        .donerProfileUrl(donerProfileUrl)
        .build();
    }

    public Heart active() {
        return Heart.builder(
                ).id(id)
                .board(board)
                .status(Status.ACTIVE)
                .donerId(donerId)
                .donerName(donerName)
                .donerProfileName(donerProfileName)
                .donerProfileUrl(donerProfileUrl)
                .build();
    }
}
