package io.nuabo.hikitty.board.application;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.application.port.HeartRepository;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.domain.Status;
import io.nuabo.hikitty.board.mock.FakeBoardRepository;
import io.nuabo.hikitty.board.mock.FakeHeartRepository;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.mock.FakeProfileRepository;
import io.nuabo.hikitty.user.mock.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HeartServiceImplTest {

    private HeartServiceImpl heartService;

    @BeforeEach
    void init() {
        BoardRepository boardRepository = new FakeBoardRepository();
        HeartRepository heartRepository = new FakeHeartRepository();
        UserRepository userRepository = new FakeUserRepository();
        ProfileRepository profileRepository = new FakeProfileRepository();
        heartService = HeartServiceImpl.builder()
                .boardRepository(boardRepository)
                .heartRepository(heartRepository)
                .userRepository(userRepository)
                .profileRepository(profileRepository)
                .build();

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
                .build();

        board = boardRepository.save(board);

        User donor = User.builder()
                .certificationCode("상태코드")
                .password("비밀번호")
                .role(Role.ROLE_DONER)
                .name("기부자 이름")
                .email("기부자 이메일")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(1234L)
                .build();

        donor = userRepository.save(donor);

        Profile profile = Profile.builder()
                .url("프로필 url")
                .originalName("프로필 원본 이름")
                .savedName("프로필 저장 이름")
                .user(donor)
                .build();
        profileRepository.save(profile);

        User donorNotWithProfile = User.builder()
                .certificationCode("상태코드")
                .password("비밀번호")
                .role(Role.ROLE_DONER)
                .name("기부자 프로필 x 이름")
                .email("기부자 프로필 x 이메일")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(1234L)
                .build();
        userRepository.save(donorNotWithProfile);

        User donorWithHeart = User.builder()
                .certificationCode("상태코드")
                .password("비밀번호")
                .role(Role.ROLE_DONER)
                .name("Heart를 가지고 있고 ACTIVE 상태인 기부자 이름")
                .email("Heart를 가지고 있는 ACTIVE 상태인 기부자 이메일")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(1234L)
                .build();

        donorWithHeart = userRepository.save(donorWithHeart);

        Heart heartACTIVE = Heart.builder()
                .board(board)
                .donerId(donorWithHeart.getId())
                .status(Status.ACTIVE)
                .donerName(donorWithHeart.getName())
                .donerProfileUrl(profile.getUrl())
                .donerProfileName(profile.getOriginalName())
                .build();
        heartRepository.save(heartACTIVE);

        User donorWithNotHeart = User.builder()
                .certificationCode("상태코드")
                .password("비밀번호")
                .role(Role.ROLE_DONER)
                .name("Heart를 가지고 있고 INACTIVE 상태인 기부자 이름")
                .email("Heart를 가지고 있는 INACTIVE 상태인 기부자 이메일")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(1234L)
                .build();

        donorWithNotHeart = userRepository.save(donorWithNotHeart);


        Heart heartINACTIVE = Heart.builder()
                .board(board)
                .donerId(donorWithNotHeart.getId())
                .status(Status.INACTIVE)
                .donerName(donorWithNotHeart.getName())
                .donerProfileUrl(profile.getUrl())
                .donerProfileName(profile.getOriginalName())
                .build();
        heartRepository.save(heartINACTIVE);
    }

    @Test
    @DisplayName("하트가 저장소에 존재하지 않고 Profile에 값이 존재하면 상태값을 ACTIVE로 생성")
    void createHeart() {
        // given
       Long boardId = 1L;
       String email = "기부자 이메일";

        // when
        Heart heart = heartService.createHeart(boardId, email);

        // then
        assertAll(
                () -> assertThat(heart.getBoard().getId()).isEqualTo(1L),
                () -> assertThat(heart.getId()).isEqualTo(3L),
                () -> assertThat(heart.getDonerId()).isEqualTo(1L),
                () -> assertThat(heart.getStatus()).isEqualTo(Status.ACTIVE),
                () -> assertThat(heart.getDonerName()).isEqualTo("기부자 이름"),
                () -> assertThat(heart.getDonerProfileUrl()).isEqualTo("프로필 url"),
                () -> assertThat(heart.getDonerProfileName()).isEqualTo("프로필 원본 이름")
        );
    }

    @Test
    @DisplayName("하트가 저장소에 존재하지 않고 Profile에 값이 존재하지 않으면 상태값을 ACTIVE로 생성")
    void createHeartNotWithProfile() {
        // given
        Long boardId = 1L;
        String email = "기부자 프로필 x 이메일";

        // when
        Heart heart = heartService.createHeart(boardId, email);

        // then
        assertAll(
                () -> assertThat(heart.getBoard().getId()).isEqualTo(1L),
                () -> assertThat(heart.getId()).isEqualTo(3L),
                () -> assertThat(heart.getDonerId()).isEqualTo(2L),
                () -> assertThat(heart.getStatus()).isEqualTo(Status.ACTIVE),
                () -> assertThat(heart.getDonerName()).isEqualTo("기부자 프로필 x 이름"),
                () -> assertThat(heart.getDonerProfileUrl()).isNull(),
                () -> assertThat(heart.getDonerProfileName()).isNull()
        );
    }


    @Test
    @DisplayName("하트가 저장소에 존재하고 기존의 상태값이 INACTIVE이면 ACTIVE로 변경")
    void createHeartAlreadyExistINACTIVE() {
        // given
        Long boardId = 1L;
        String email = "Heart를 가지고 있는 INACTIVE 상태인 기부자 이메일";

        // when
        Heart heart = heartService.createHeart(boardId, email);

        // then
        assertAll(
                () -> assertThat(heart.getBoard().getId()).isEqualTo(1L),
                () -> assertThat(heart.getId()).isEqualTo(3L),
                () -> assertThat(heart.getDonerId()).isEqualTo(4L),
                () -> assertThat(heart.getStatus()).isEqualTo(Status.ACTIVE),
                () -> assertThat(heart.getDonerName()).isEqualTo("Heart를 가지고 있고 INACTIVE 상태인 기부자 이름"),
                () -> assertThat(heart.getDonerProfileUrl()).isNull(),
                () -> assertThat(heart.getDonerProfileName()).isNull()
        );
    }

    @Test
    @DisplayName("하트가 저장소에 존재하고 기존의 상태값이 ACTIVE이면 그냥 출력")
    void createHeartAlreadyExistACTIVE() {
        // given
        Long boardId = 1L;
        String email = "Heart를 가지고 있는 ACTIVE 상태인 기부자 이메일";

        // when
        Heart heart = heartService.createHeart(boardId, email);

        // then
        assertAll(
                () -> assertThat(heart.getBoard().getId()).isEqualTo(1L),
                () -> assertThat(heart.getId()).isEqualTo(3L),
                () -> assertThat(heart.getDonerId()).isEqualTo(3L),
                () -> assertThat(heart.getStatus()).isEqualTo(Status.ACTIVE),
                () -> assertThat(heart.getDonerName()).isEqualTo("Heart를 가지고 있고 ACTIVE 상태인 기부자 이름"),
                () -> assertThat(heart.getDonerProfileUrl()).isNull(),
                () -> assertThat(heart.getDonerProfileName()).isNull()
        );
    }

    @Test
    @DisplayName("하트가 존재하고 Active일때 INACTIVE로 변경")
    void deleteHeart() {
        // given
        Long heartId = 1L;

        // when
        Heart heart = heartService.deleteHeart(heartId);

        // then
        assertAll(
                () -> assertThat(heart.getId()).isEqualTo(1L),
                () -> assertThat(heart.getStatus()).isEqualTo(Status.INACTIVE)
        );
    }

    @Test
    @DisplayName("하트가 존재하지 않을 때 예외 발생")
    void deleteHeartException() {
        // given
        Long heartId = 3L;

        // when
        // then
        assertThatThrownBy(() -> heartService.deleteHeart(heartId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Heart에서 ID 3를 찾을 수 없습니다.");
    }


}