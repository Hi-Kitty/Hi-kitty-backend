package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.board.domain.Fundraiser;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Getter
@Entity(name = "fundraiser")
@SQLDelete(sql = "update fundraiser set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class FundraiserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Comment("후원자 아이디")
    @Column(nullable = false)
    private Long fundraiserId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profileName;

    @Column(nullable = false)
    private String profileUrl;

    public static FundraiserEntity from(Fundraiser fundraiser) {
        FundraiserEntity fundraiserEntity = new FundraiserEntity();
        fundraiserEntity.id = fundraiser.getId();
        fundraiserEntity.boardEntity = BoardEntity.from(fundraiser.getBoard());
        fundraiserEntity.fundraiserId = fundraiser.getFundraiserId();
        fundraiserEntity.name = fundraiser.getName();
        fundraiserEntity.profileName = fundraiser.getProfileName();
        fundraiserEntity.profileUrl = fundraiser.getProfileUrl();
        return fundraiserEntity;
    }

    public Fundraiser toModel() {
        return Fundraiser.builder()
                .id(id)
                .board(boardEntity.toModel())
                .fundraiserId(fundraiserId)
                .name(name)
                .profileName(profileName)
                .profileUrl(profileUrl)
                .build();
    }
}
