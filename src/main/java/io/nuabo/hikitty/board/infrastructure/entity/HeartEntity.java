package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
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

}
