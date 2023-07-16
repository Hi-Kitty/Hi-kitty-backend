package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BoardTest {

    @Test
    @DisplayName("Board를 BoardCreateRequest를 이용하여 생성한다.")
    void from() {
        // given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .title("제목")
                .content("컨텐트")
                .subTitle("서브타이틀")
                .targetAmount(10000L)
                .endAt(LocalDate.parse("2021-08-01"))
                .build();
        // when
        Board board = Board.from(boardCreateRequest);
        // then
        assertAll(
                () -> assertThat(board.getTitle()).isEqualTo("제목"),
                () -> assertThat(board.getContent()).isEqualTo("컨텐트"),
                () -> assertThat(board.getSubTitle()).isEqualTo("서브타이틀"),
                () -> assertThat(board.getTargetAmount()).isEqualTo(10000L),
                () -> assertThat(board.getEndAt()).isEqualTo(LocalDateTime.parse("2021-08-01T00:00:00"))
        );


    }

    @Test
    @DisplayName("Board를 BoardCreateRequest와 User, Profile를 이용하여 생성한다.")
    void testFrom() {
        // given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .title("제목")
                .content("컨텐트")
                .subTitle("서브타이틀")
                .targetAmount(10000L)
                .endAt(LocalDate.parse("2021-08-01"))
                .build();
        User user = User.builder()
                .name("이름")
                .id(1L)
                .email("이메일")
                .lastLoginAt(123456L)
                .certificationCode("인증코드")
                .status(UserStatus.ACTIVE)
                .password("비밀번호")
                .build();
        Profile profile = Profile.builder()
                .originalName("원본이름")
                .savedName("저장이름")
                .user(user)
                .url("url")
                .build();
        // when
        Board board = Board.from(boardCreateRequest, user, profile);

        // then
        assertAll(
                () -> assertThat(board.getTitle()).isEqualTo("제목"),
                () -> assertThat(board.getContent()).isEqualTo("컨텐트"),
                () -> assertThat(board.getSubTitle()).isEqualTo("서브타이틀"),
                () -> assertThat(board.getTargetAmount()).isEqualTo(10000L),
                () -> assertThat(board.getEndAt()).isEqualTo(LocalDateTime.parse("2021-08-01T00:00:00")),
                () -> assertThat(board.getFundraiserId()).isEqualTo(1L),
                () -> assertThat(board.getFundraiserName()).isEqualTo("이름"),
                () -> assertThat(board.getFundraiserProfileName()).isEqualTo("원본이름"),
                () -> assertThat(board.getFundraiserProfileUrl()).isEqualTo("url")
        );
    }

    @Test
    @DisplayName("Board를 BoardCreateRequest와 User를 이용하여 생성한다.")
    void testFrom1() {
        // given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .title("제목")
                .content("컨텐트")
                .subTitle("서브타이틀")
                .targetAmount(10000L)
                .endAt(LocalDate.parse("2021-08-01"))
                .build();
        User user = User.builder()
                .name("이름")
                .id(1L)
                .email("이메일")
                .lastLoginAt(123456L)
                .certificationCode("인증코드")
                .status(UserStatus.ACTIVE)
                .password("비밀번호")
                .build();
        // when
        Board board = Board.from(boardCreateRequest, user);

        // then
        assertAll(
                () -> assertThat(board.getTitle()).isEqualTo("제목"),
                () -> assertThat(board.getContent()).isEqualTo("컨텐트"),
                () -> assertThat(board.getSubTitle()).isEqualTo("서브타이틀"),
                () -> assertThat(board.getTargetAmount()).isEqualTo(10000L),
                () -> assertThat(board.getEndAt()).isEqualTo(LocalDateTime.parse("2021-08-01T00:00:00")),
                () -> assertThat(board.getFundraiserId()).isEqualTo(1L),
                () -> assertThat(board.getFundraiserName()).isEqualTo("이름")
        );
    }

    @Test
    @DisplayName("Board를 BoardCreateRequest와 FundraiserCreate를 이용하여 생성한다.")
    void testFrom2() {
        // given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .title("제목")
                .content("컨텐트")
                .subTitle("서브타이틀")
                .targetAmount(10000L)
                .endAt(LocalDate.parse("2021-08-01"))
                .build();
        FundraiserCreate fundraiserCreate = FundraiserCreate.builder()
                .name("이름")
                .profileName("프로필이름")
                .profileUrl("url")
                .build();
        // when
        Board board = Board.from(boardCreateRequest, fundraiserCreate);

        // then
        assertAll(
                () -> assertThat(board.getTitle()).isEqualTo("제목"),
                () -> assertThat(board.getContent()).isEqualTo("컨텐트"),
                () -> assertThat(board.getSubTitle()).isEqualTo("서브타이틀"),
                () -> assertThat(board.getTargetAmount()).isEqualTo(10000L),
                () -> assertThat(board.getEndAt()).isEqualTo(LocalDateTime.parse("2021-08-01T00:00:00")),
                () -> assertThat(board.getFundraiserName()).isEqualTo("이름"),
                () -> assertThat(board.getFundraiserProfileName()).isEqualTo("프로필이름"),
                () -> assertThat(board.getFundraiserProfileUrl()).isEqualTo("url")
        );
    }

    @Test
    @DisplayName("Board의 currentAmount 양이 증가한다.")
    void increasePaidAmount() {
        // given
        Board board = Board.builder()
                .currentAmount(10000L)
                .build();
        // when
        board = board.increasePaidAmount(1000L);
        // then
        assertThat(board.getCurrentAmount()).isEqualTo(11000L);
    }

    @Test
    @DisplayName("Board의 fundraiserImage와 관련된 내용을 변경한다.")
    void updateImg() {
        // given
        Board board = Board.builder()
                .fundraiserProfileName("이전이름")
                .fundraiserProfileUrl("이전url")
                .build();
        AmazonS3Upload amazonS3Upload = AmazonS3Upload.builder()
                .url("이후url")
                .savedName("저장이름")
                .originalName("원본이름")
                .build();
        // when
        board = board.updateImg(amazonS3Upload);
        // then
        Board finalBoard = board;
        assertAll(
                () -> assertThat(finalBoard.getFundraiserProfileName()).isEqualTo("원본이름"),
                () -> assertThat(finalBoard.getFundraiserProfileUrl()).isEqualTo("이후url")
        );
    }
}