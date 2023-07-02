package io.nuabo.hikitty.board.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.board.domain.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;


@Getter
@Entity(name = "plan")
@SQLDelete(sql = "update plan set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class PlanEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private Long amount;

    public static PlanEntity from(Plan plan) {
        PlanEntity planEntity = new PlanEntity();
        planEntity.id = plan.getId();
        planEntity.boardEntity = BoardEntity.from(plan.getBoard());
        planEntity.reason = plan.getReason();
        planEntity.amount = plan.getAmount();
        return planEntity;
    }

    public static List<PlanEntity> froms(List<Plan> plans) {
        return plans.stream()
                .map(PlanEntity::from)
                .toList();
    }

    public Plan toModel() {
        return Plan.builder()
                .id(id)
                .board(boardEntity.toModel())
                .reason(reason)
                .amount(amount)
                .build();
    }
}
