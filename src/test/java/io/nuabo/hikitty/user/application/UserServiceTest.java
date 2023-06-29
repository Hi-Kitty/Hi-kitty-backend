package io.nuabo.hikitty.user.application;

import io.nuabo.common.domain.exception.CertificationCodeNotMatchedException;
import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.mock.TestUuidHolder;
import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.mock.FakeMailSender;
import io.nuabo.hikitty.user.mock.FakeMailSenderConfig;
import io.nuabo.hikitty.user.mock.FakeUserRepository;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {
    private UserServiceImpl userService;
    @BeforeEach()
    void init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakeMailSenderConfig fakeMailSenderConfig = new FakeMailSenderConfig(
                "http://localhost:8080/api/v0/users/",
                "/verify?certificationCode=",
                "제목입니다.",
                "내용입니다."
        );
        this.userService = UserServiceImpl.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .userRepository(fakeUserRepository)
                .certificationService(new CertificationService(fakeMailSender, fakeMailSenderConfig))
                .build();
        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("spring2@naver.com")
                .name("kok202")
                .password("1234")
                .role(Role.ROLE_DONER)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("spring@naver.com")
                .password("1234")
                .role(Role.ROLE_DONER)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());
    }


    @Test
    @DisplayName("create는 유저를 생성한다")
    void create() {
        // given
        UserCreateRequest userCreate = UserCreateRequest.builder()
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .build();


        // when
        User result = userService.create(userCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }

    @Test
    @DisplayName("getByEmail은 Active 상태인 유저를 찾을 수 있다")
    void getByEmailIsActive() {
        // given
        String email = "spring2@naver.com";

        // when
        User result = userService.getByEmail(email);

        // then
        assertThat(result.getName()).isEqualTo("kok202");
    }
    @Test
    @DisplayName("getByEmail은 Pending 상태인 유저를 찾을 수 없다")
    void doseNotGetByEmailIsPending() {

        // given
        String email = "spring@naver.com";

        // when
        // then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }
    @Test
    @DisplayName("getByEmail은 Pending 상태인 유저를 찾을 수 있다")
    void getByIdIsActive() {
        // given
        long id = 1L;

        // when
        User result = userService.getById(id);

        // then
        assertThat(result.getName()).isEqualTo("kok202");
    }

    @Test
    @DisplayName("getById는 Pending 상태인 유저를 찾을 수 없다")
    void doseNotGetByIdIsPending() {
        // given
        long id = 2L;

        // when
        // then
        assertThatThrownBy(() -> {
            User result = userService.getById(id);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update() {
    }


    @Test
    @DisplayName("PENDING 상태의 사용자는 인증 코드로 ACTIVE 시킬 수 있다.")
    void verifyEmail() {
        // given
        // when
        userService.verifyEmail(2, "aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
        // then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("PENDING 상태의 사용자는 잘못된 인증코드를 받으면 에러를 던진다.")
    void doseNotUpdatedPendingUserToActiveUserByVerifyCode() {

        // given
        // when
        // then
        assertThatThrownBy(() -> userService
                .verifyEmail(2, "aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);

    }
}