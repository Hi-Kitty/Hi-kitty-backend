package io.nuabo.hikitty.user.domain;

import io.nuabo.common.domain.exception.CertificationCodeNotMatchedException;
import io.nuabo.hikitty.mock.TestClockHolder;
import io.nuabo.hikitty.mock.TestUuidHolder;
import io.nuabo.hikitty.security.application.port.PasswordEncoderHolder;
import io.nuabo.hikitty.user.mock.TestPasswordEncoderHolder;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserTest {

    @Test
    @DisplayName("User는 UserCreateRequest 객체로 생성할 수 있다.")
    void from() {
        // given
        UserCreateRequest userCreate = UserCreateRequest.builder()
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("spring3@naver.com");
        assertThat(user.getName()).isEqualTo("푸항항");
        assertThat(user.getPassword()).isEqualTo("12354721");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    @DisplayName("User는 인코딩된 비밀번호와 함께 생성할 수 있다.")
    void register() {
        // given
        UserCreateRequest userCreate = UserCreateRequest.builder()
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .build();
        // when
        User user = User.register(userCreate, new TestPasswordEncoderHolder(), new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("spring3@naver.com");
        assertThat(user.getName()).isEqualTo("푸항항");
        assertThat(user.getPassword()).isEqualTo("12354721");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }

    @Test
    @DisplayName("기존의 User가 있을시 ID값과 PENDING만 이전 값을 유지한다.")
    void check() {
        // given
        User beforeUser = User.builder()
                .id(1L)
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        User user = User.builder()
                .id(2L)
                .email("spring4@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항2")
                .password("222222")
                .status(UserStatus.PENDING)
                .build();
        // when
        user = user.check(beforeUser.getId(), new TestUuidHolder("ccccccc-cccc-cccc-cccccccccccc"));

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("spring4@naver.com");
        assertThat(user.getName()).isEqualTo("푸항항2");
        assertThat(user.getPassword()).isEqualTo("222222");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("ccccccc-cccc-cccc-cccccccccccc");
    }


    @Test
    @DisplayName("User는 인증 코드로 PENDING -> ACTIVE로 계정을 활성화 할 수 있다.")
    void certificate() {
        // given
        User user = User.builder()
                .id(1L)
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(100L)
                .build();
        // when
        user = user.certificate("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa", user.getCertificationCode());

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("spring3@naver.com");
        assertThat(user.getName()).isEqualTo("푸항항");
        assertThat(user.getPassword()).isEqualTo("12354721");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }
    @Test
    @DisplayName("User는 잘못된 인증 코드로 예외가 발생한다.")
    void certificateException() {
        // given
        User user = User.builder()
                .id(1L)
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .status(UserStatus.PENDING)
                .certificationCode("bbbbbbb-bbbb-bbbb-bbbbbbbbbbbb")
                .lastLoginAt(100L)
                .build();
        // when then
        assertThatThrownBy(()->
                user.certificate("aaaaaaa-aaaa-aaaa-aaabbbb", user.getCertificationCode()))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    @DisplayName("로그인 시 lastLoginAt에 새로운 값이 들어간다.")
    void login() {
        // given
        User user = User.builder()
                .id(1L)
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .status(UserStatus.ACTIVE)
                .certificationCode("bbbbbbb-bbbb-bbbb-bbbbbbbbbbbb")
                .lastLoginAt(100L)
                .build();
        // when
        user = user.login(new TestClockHolder(200L));

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("spring3@naver.com");
        assertThat(user.getName()).isEqualTo("푸항항");
        assertThat(user.getPassword()).isEqualTo("12354721");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("bbbbbbb-bbbb-bbbb-bbbbbbbbbbbb");
        assertThat(user.getLastLoginAt()).isEqualTo(200L);

    }

    @Test
    @DisplayName("User는 비밀번호 또는 이름을 변경할 수 있다.")
    void update() {
        PasswordEncoderHolder passwordEncoder = new TestPasswordEncoderHolder();
        // given
        User user = User.builder()
                .id(1L)
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .status(UserStatus.ACTIVE)
                .certificationCode("bbbbbbb-bbbb-bbbb-bbbbbbbbbbbb")
                .lastLoginAt(100L)
                .build();

        UserUpdateRequest request = UserUpdateRequest.builder()
                .name("푸항항2")
                .password("222222")
                .build();

        // when
        user = user.update(request, passwordEncoder);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("spring3@naver.com");
        assertThat(user.getName()).isEqualTo("푸항항2");
        assertThat(user.getPassword()).isEqualTo("222222");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("bbbbbbb-bbbb-bbbb-bbbbbbbbbbbb");
        assertThat(user.getLastLoginAt()).isEqualTo(100L);

    }

}