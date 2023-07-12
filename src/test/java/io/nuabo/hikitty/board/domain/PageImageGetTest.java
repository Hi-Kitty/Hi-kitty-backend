package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.mock.TestClockHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PageImageGetTest {

    @Test
    @DisplayName("PageImageGet를 Board와 percent calculator를 이용하여 생성한다.")
    void from() {
        // given
        Board board = Board.builder()
                .title("제목")
                .content("컨텐트")
                .subTitle("서브타이틀")
                .targetAmount(100000L)
                .endAt(LocalDateTime.parse("2021-08-10T00:00:00"))
                .fundraiserId(1L)
                .fundraiserName("모금자 이름")
                .fundraiserProfileUrl("모금자 프로필 url")
                .fundraiserProfileName("모금자 프로필 이름")
                .createdAt(LocalDateTime.parse("2021-08-01T00:00:00"))
                .currentAmount(10000L)
                .id(1L)
                .build();
        Image image = Image.builder()
                .id(1L)
                .originalName("게시판 이미지 원본 이름")
                .savedName("게시판 이미지 저장 이름")
                .board(board)
                .url("게시판 이미지 url")
                .build();
        TestClockHolder testClockHolder = new TestClockHolder(1000L);

        // when
        PageImageGet pageImageGet = PageImageGet.from(image, testClockHolder);
        // then
        assertAll(
                () -> assertThat(pageImageGet.getId()).isEqualTo(1L),
                () -> assertThat(pageImageGet.getDDay()).isEqualTo(9L),
                () -> assertThat(pageImageGet.getTitle()).isEqualTo("제목"),
                () -> assertThat(pageImageGet.getFundraiserId()).isEqualTo(1L),
                () -> assertThat(pageImageGet.getFundraiserName()).isEqualTo("모금자 이름"),
                () -> assertThat(pageImageGet.getFundraiserProfileUrl()).isEqualTo("모금자 프로필 url"),
                () -> assertThat(pageImageGet.getFundraiserProfileName()).isEqualTo("모금자 프로필 이름"),
                () -> assertThat(pageImageGet.getPercent()).isEqualTo(10L),
                () -> assertThat(pageImageGet.getCreatedAt()).isEqualTo(LocalDateTime.parse("2021-08-01T00:00:00")),
                () -> assertThat(pageImageGet.getImageUrl()).isEqualTo("게시판 이미지 url"),
                () -> assertThat(pageImageGet.getImageName()).isEqualTo("게시판 이미지 원본 이름"),
                () -> assertThat(pageImageGet.getEndAt()).isEqualTo(LocalDateTime.parse("2021-08-10T00:00:00"))
        );
    }
}