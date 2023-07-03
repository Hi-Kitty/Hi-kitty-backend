package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Plan;

import java.util.List;

public interface PlanRepository {
    Plan save(Plan plan);

    List<Plan> saveAll(List<Plan> plans);

    List<Plan> findAllByBoardId(Long boardId);


}
