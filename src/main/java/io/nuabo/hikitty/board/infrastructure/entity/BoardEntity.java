package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long targetAmount;

    @Column(columnDefinition = "bigint default 0")
    private Long currentAmount;

    @Column(nullable = false)
    private LocalDateTime endAt;

}
