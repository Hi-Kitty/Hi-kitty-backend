package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.board.domain.Board;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;


@Getter
@Entity(name = "board")
@SQLDelete(sql = "update board set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class BoardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Comment("부제목")
    @Column(nullable = false)
    private String subTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long targetAmount;

    @Column(columnDefinition = "bigint default 0")
    private Long currentAmount;

    @Column(nullable = false)
    private LocalDateTime endAt;

    private Long fundraiserId;

    private String fundraiserName;

    private String fundraiserProfileName;

    private String fundraiserProfileUrl;

    public static BoardEntity from(Board board) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.id = board.getId();
        boardEntity.title = board.getTitle();
        boardEntity.subTitle = board.getSubTitle();
        boardEntity.content = board.getContent();
        boardEntity.targetAmount = board.getTargetAmount();
        boardEntity.currentAmount = board.getCurrentAmount();
        boardEntity.endAt = board.getEndAt();
        boardEntity.fundraiserId = board.getFundraiserId();
        boardEntity.fundraiserName = board.getFundraiserName();
        boardEntity.fundraiserProfileName = board.getFundraiserProfileName();
        boardEntity.fundraiserProfileUrl = board.getFundraiserProfileUrl();
        return boardEntity;
    }

    public Board toModel() {
        return Board.builder()
                .id(id)
                .title(title)
                .subTitle(subTitle)
                .content(content)
                .targetAmount(targetAmount)
                .currentAmount(currentAmount)
                .endAt(endAt)
                .fundraiserId(fundraiserId)
                .fundraiserName(fundraiserName)
                .fundraiserProfileName(fundraiserProfileName)
                .fundraiserProfileUrl(fundraiserProfileUrl)
                .createdAt(this.getCreateAt())
                .build();
    }
}
