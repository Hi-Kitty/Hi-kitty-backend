package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HeartTest {

    @Test
    @DisplayName("Heart를 Board와 User, Profile를 이용하여 상태를 INACTIVE로 생성한다.")
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
        User user = User.builder()
                .id(1L)
                .name("기부자 이름")
                .build();
        Profile profile = Profile.builder()
                .id(1L)
                .user(user)
                .originalName("프로필 원본 이름")
                .url("프로필 url")
                .build();

        // when
        Heart heart = Heart.from(board, user, profile);

        // then
        assertAll(
                () -> assertThat(heart.getBoard()).isEqualTo(board),
                () -> assertThat(heart.getStatus()).isEqualTo(Status.ACTIVE),
                () -> assertThat(heart.getDonerId()).isEqualTo(1L),
                () -> assertThat(heart.getDonerName()).isEqualTo("기부자 이름"),
                () -> assertThat(heart.getDonerProfileUrl()).isEqualTo("프로필 url"),
                () -> assertThat(heart.getDonerProfileName()).isEqualTo("프로필 원본 이름")
        );
    }

    @Test
    @DisplayName("Heart를 Board와 User를 이용하여 상태를 ACTIVE로 생성한다.")
    void testFrom() {
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
        User user = User.builder()
                .id(1L)
                .name("기부자 이름")
                .build();
        // when
        Heart heart = Heart.from(board, user);

        // then
        assertAll(
                () -> assertThat(heart.getBoard()).isEqualTo(board),
                () -> assertThat(heart.getStatus()).isEqualTo(Status.ACTIVE),
                () -> assertThat(heart.getDonerId()).isEqualTo(1L),
                () -> assertThat(heart.getDonerName()).isEqualTo("기부자 이름"),
                () -> assertThat(heart.getDonerProfileUrl()).isNull(),
                () -> assertThat(heart.getDonerProfileName()).isNull()
        );
    }

    @Test
    @DisplayName("Heart의 상태값을 INACTIVE로 변경한다.")
    void inActivce() {
        // given
        Heart heart = Heart.builder()
                .status(Status.ACTIVE)
                .build();
        // when
        heart = heart.inActivce();

        // then
        assertThat(heart.getStatus()).isEqualTo(Status.INACTIVE);
    }

    @Test
    @DisplayName("Heart의 상태값을 ACTIVE로 변경한다.")
    void active() {
        // given
        Heart heart = Heart.builder()
                .status(Status.INACTIVE)
                .build();
        // when
        heart = heart.active();

        // then
        assertThat(heart.getStatus()).isEqualTo(Status.ACTIVE);

    }

    @Test
    @DisplayName("Heart안에 있는 donerProfile를 변경한다.")
    void updateImg() {
        // given
        Heart heart = Heart.builder()
                .donerProfileName("프로필 원본 이름")
                .donerProfileUrl("프로필 url")
                .build();
        AmazonS3Upload amazonS3Upload = AmazonS3Upload.builder()
                .originalName("프로필 원본 이름2")
                .url("프로필 url2")
                .build();
        // when
        heart = heart.updateImg(amazonS3Upload);

        // then
        Heart finalHeart = heart;
        assertAll(
                () -> assertThat(finalHeart.getDonerProfileName()).isEqualTo("프로필 원본 이름2"),
                () -> assertThat(finalHeart.getDonerProfileUrl()).isEqualTo("프로필 url2")
        );
    }
}