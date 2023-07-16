package io.nuabo.hikitty.board.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardFundraiserImagePlanTest {

    @Test
    @DisplayName("BoardFundraiserImagePlan를 Board, Image, Plan을 이용하여 생성한다.")
    void from() {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("컨텐트")
                .subTitle("서브타이틀")
                .targetAmount(100000L)
                .endAt(LocalDateTime.parse("2021-08-01T00:00:00"))
                .fundraiserId(1L)
                .fundraiserName("모금자 이름")
                .fundraiserProfileUrl("모금자 프로필 url")
                .fundraiserProfileName("모금자 프로필 이름")
                .createdAt(LocalDateTime.parse("2021-08-01T00:00:00"))
                .currentAmount(0L)
                .id(1L)
                .build();
        Image image = Image.builder()
                .id(1L)
                .originalName("게시판 이미지 원본 이름")
                .savedName("게시판 이미지 저장 이름")
                .board(board)
                .url("게시판 이미지 url")
                .build();
        Plan plan = Plan.builder()
                .id(1L)
                .amount(10000L)
                .board(board)
                .createdAt(LocalDateTime.parse("2021-08-01T00:00:00"))
                .reason("기부 이유")
                .build();
        List<Plan> plans = List.of(plan);
        // when
        BoardFundraiserImagePlan boardFundraiserImagePlan = BoardFundraiserImagePlan.from(board, image, plans);
        // then
        assertAll(
                () -> assertEquals(board, boardFundraiserImagePlan.getBoard()),
                () -> assertEquals(image, boardFundraiserImagePlan.getImage()),
                () -> assertEquals(plan, boardFundraiserImagePlan.getPlans().get(0))
        );
    }
}