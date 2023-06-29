package io.nuabo.hikitty.user.presentation;

import io.nuabo.common.domain.exception.CertificationCodeNotMatchedException;
import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.mock.FakeMailSenderConfig;
import io.nuabo.hikitty.user.mock.TestUserContainer;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserControllerTest {

    @Test
    @DisplayName("사용자는 회원가입을 할 수 있고 회원가입된 사용자는 PENDING 상태이다.")
    void create() {
        // given
        TestUserContainer container = TestUserContainer.builder()
                .uuidHolder(() -> "aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .mailSenderConfig(new FakeMailSenderConfig(
                        "http://localhost:8080/api/v0/users/",
                        "/verify?certificationCode=",
                        "제목입니다.",
                        "내용입니다: "
                )).build();
        UserCreateRequest userCreate = UserCreateRequest.builder()
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .build();
//        // when
        ResponseEntity<ApiUtils.ApiResult<UserResponse>> result = container.userController.create(userCreate);
//        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getResponse().getId()).isEqualTo(1);
        assertThat(result.getBody().getResponse().getEmail()).isEqualTo("spring3@naver.com");
        assertThat(result.getBody().getResponse().getName()).isEqualTo("푸항항");
        assertThat(result.getBody().getResponse().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getBody().getResponse().getRole()).isEqualTo(Role.ROLE_DONER);
        assertThat(container.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");


    }
    @Test
    @DisplayName("사용자는 인증 코드로 계정을 활성화 시킬 수 있다.")
    void verifyEmail() {
        // given
        TestUserContainer container = TestUserContainer.builder()
                .uuidHolder(() -> "aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .mailSenderConfig(new FakeMailSenderConfig(
                        "http://localhost:8080/api/v0/users/",
                        "/verify?certificationCode=",
                        "제목입니다.",
                        "내용입니다: "
                )).build();
        container.userRepository.save(User.builder()
                .id(1L)
                .email("spring4@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항2")
                .password("222222")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build());
        // when
        ResponseEntity<Void> result = container.userController.verifyEmail(1L, "aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        assertThat(container.userRepository.getById(1).getStatus()).isEqualTo((UserStatus.ACTIVE));
    }
    @Test
    @DisplayName("사용자는 인증 코드가 일치하지 않을 경우 권한 없음 에러를 내려준다.")
    void verifyEmailByException() {
        // given
        TestUserContainer container = TestUserContainer.builder()
                .build();
        container.userRepository.save(User.builder()
                .id(1L)
                .email("spring4@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항2")
                .password("222222")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build());
        // when
        // then
        assertThatThrownBy(() -> container.userController.verifyEmail(1, "bbbbbbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);

    }
    @Test
    @DisplayName("Contoller에서 getByEmail은 Active 상태인 유저를 찾을 수 있다")
    void getById() {
        // given
        TestUserContainer container = TestUserContainer.builder()
                .build();
        container.userRepository.save(User.builder()
                .id(1L)
                .email("spring4@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항2")
                .password("222222")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build());
        // when
        ResponseEntity<ApiUtils.ApiResult<UserResponse>> result = container.userController.getById(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getResponse().getId()).isEqualTo(1);
        assertThat(result.getBody().getResponse().getEmail()).isEqualTo("spring4@naver.com");
        assertThat(result.getBody().getResponse().getName()).isEqualTo("푸항항2");
        assertThat(result.getBody().getResponse().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getResponse().getRole()).isEqualTo(Role.ROLE_DONER);
    }
}