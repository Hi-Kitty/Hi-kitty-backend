package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;
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

    private final Long fundraiserId;

    private final String fundraiserName;

    private final String fundraiserProfileName;

    private final String fundraiserProfileUrl;
    @Builder
    public Board(Long id, String title, String subTitle, String content, Long targetAmount, Long currentAmount, LocalDateTime endAt, LocalDateTime createdAt, Long fundraiserId, String fundraiserName, String fundraiserProfileName, String fundraiserProfileUrl) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.endAt = endAt;
        this.createdAt = createdAt;
        this.fundraiserId = fundraiserId;
        this.fundraiserName = fundraiserName;
        this.fundraiserProfileName = fundraiserProfileName;
        this.fundraiserProfileUrl = fundraiserProfileUrl;
    }

    public static Board from(BoardCreateRequest boardCreateRequest) {
        return Board.builder()
                .title(boardCreateRequest.getTitle())
                .subTitle(boardCreateRequest.getSubTitle())
                .content(boardCreateRequest.getContent())
                .targetAmount(boardCreateRequest.getTargetAmount())
                .endAt(boardCreateRequest.getEndAt().atStartOfDay())
                .currentAmount(0L)
                .build();
    }

    public static Board from(BoardCreateRequest boardCreateRequest, User user, Profile profile) {
        return Board.builder()
                .title(boardCreateRequest.getTitle())
                .subTitle(boardCreateRequest.getSubTitle())
                .content(boardCreateRequest.getContent())
                .targetAmount(boardCreateRequest.getTargetAmount())
                .endAt(boardCreateRequest.getEndAt().atStartOfDay())
                .currentAmount(0L)
                .fundraiserProfileName(profile.getOriginalName())
                .fundraiserName(user.getName())
                .fundraiserProfileUrl(profile.getUrl())
                .fundraiserId(user.getId())
                .build();
    }

    public static Board from(BoardCreateRequest boardCreateRequest, User user) {
        return Board.builder()
                .title(boardCreateRequest.getTitle())
                .subTitle(boardCreateRequest.getSubTitle())
                .content(boardCreateRequest.getContent())
                .targetAmount(boardCreateRequest.getTargetAmount())
                .endAt(boardCreateRequest.getEndAt().atStartOfDay())
                .currentAmount(0L)
                .fundraiserName(user.getName())
                .fundraiserId(user.getId())
                .build();
    }


    public static Board from(BoardCreateRequest boardCreateRequest, FundraiserCreate fundraiserCreate) {
        return Board.builder()
                .title(boardCreateRequest.getTitle())
                .subTitle(boardCreateRequest.getSubTitle())
                .content(boardCreateRequest.getContent())
                .targetAmount(boardCreateRequest.getTargetAmount())
                .endAt(boardCreateRequest.getEndAt().atStartOfDay())
                .currentAmount(0L)
                .fundraiserProfileName(fundraiserCreate.getProfileName())
                .fundraiserName(fundraiserCreate.getName())
                .fundraiserProfileUrl(fundraiserCreate.getProfileUrl())
                .fundraiserId(fundraiserCreate.getId())
                .build();
    }

    public Board increasePaidAmount(Long amount) {
        return Board.builder()
                .id(this.id)
                .title(this.title)
                .subTitle(this.subTitle)
                .content(this.content)
                .targetAmount(this.targetAmount)
                .endAt(this.endAt)
                .currentAmount(this.currentAmount + amount)
                .fundraiserProfileName(this.fundraiserProfileName)
                .fundraiserName(this.fundraiserName)
                .fundraiserProfileUrl(this.fundraiserProfileUrl)
                .fundraiserId(this.fundraiserId)
                .build();
    }
    public Board updateImg(AmazonS3Upload amazonS3Upload) {
        return Board.builder()
                .id(this.id)
                .title(this.title)
                .subTitle(this.subTitle)
                .content(this.content)
                .targetAmount(this.targetAmount)
                .endAt(this.endAt)
                .currentAmount(this.currentAmount)
                .fundraiserProfileName(amazonS3Upload.getOriginalName())
                .fundraiserName(this.fundraiserName)
                .fundraiserProfileUrl(amazonS3Upload.getUrl())
                .fundraiserId(this.fundraiserId)
                .build();
    }
}
