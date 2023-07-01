package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity(name = "doner")
@SQLDelete(sql = "update doner set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class DonerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "heart_id", nullable = false)
    private HeartEntity heartEntity;

    @Column(nullable = false)
    private Long donerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profileName;

    @Column(nullable = false)
    private String profileUrl;

}
