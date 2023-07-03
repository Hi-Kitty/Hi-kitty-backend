package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.domain.Status;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity(name = "heart")
@SQLDelete(sql = "update heart set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class HeartEntity extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Comment("하트 상태 ACTIVE, INACTIVE")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Long donerId;

    @Column(nullable = false)
    private String donerName;

    @Comment("후원자 프로필 이름 - null 가능")
    @Column
    private String donerProfileName;

    @Comment("후원자 프로필 사진 - null 가능")
    @Column
    private String donerProfileUrl;

    public static HeartEntity from(Heart heart) {
        HeartEntity heartEntity = new HeartEntity();
        heartEntity.id = heart.getId();
        heartEntity.boardEntity = BoardEntity.from(heart.getBoard());
        heartEntity.status = heart.getStatus();
        heartEntity.donerId = heart.getDonerId();
        heartEntity.donerName = heart.getDonerName();
        heartEntity.donerProfileName = heart.getDonerProfileName();
        heartEntity.donerProfileUrl = heart.getDonerProfileUrl();
        return heartEntity;
    }

    public Heart toModel() {
        return Heart.builder()
                .id(id)
                .board(boardEntity.toModel())
                .status(status)
                .donerId(donerId)
                .donerName(donerName)
                .donerProfileName(donerProfileName)
                .donerProfileUrl(donerProfileUrl)
                .createdAt(this.getCreateAt())
                .build();
    }
}
