package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.hikitty.board.application.port.PlanRepository;
import io.nuabo.hikitty.board.domain.Plan;
import io.nuabo.hikitty.board.infrastructure.entity.PlanEntity;
import io.nuabo.hikitty.board.infrastructure.port.PlanJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepository {
    private final PlanJpaRepository planJpaRepository;

    @Override
    public Plan save(Plan plan) {
        return planJpaRepository.save(PlanEntity.from(plan)).toModel();
    }

    @Override
    public List<Plan> saveAll(List<Plan> plans) {
        return planJpaRepository.saveAll(PlanEntity.froms(plans)).stream().map(PlanEntity::toModel).toList();
    }
}
