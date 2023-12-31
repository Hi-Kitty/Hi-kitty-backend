package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PlanTest {

    @Test
    @DisplayName("Plan를 Board와 AmazonS3Upload을 이용하여 생성한다.")
    void from() {
        // given
        Board board = Board.builder()
                .id(1L)
                .build();
        PlanCreateRequest planCreateRequest = PlanCreateRequest.builder()
                .amount(10000L)
                .reason("설명")
                .build();
        // when
        Plan plan = Plan.from(board, planCreateRequest);
        // then
        assertAll(
                () -> assertThat(plan.getAmount()).isEqualTo(10000L),
                () -> assertThat(plan.getReason()).isEqualTo("설명"),
                () -> assertThat(plan.getBoard()).isEqualTo(board)
        );
    }
}