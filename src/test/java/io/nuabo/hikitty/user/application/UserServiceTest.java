package io.nuabo.hikitty.user.application;

import io.nuabo.common.domain.exception.CertificationCodeNotMatchedException;
import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.amazons3.mock.FakeAmazonS3ClientHolder;
import io.nuabo.hikitty.mock.TestDefaultImageConfig;
import io.nuabo.hikitty.mock.TestUuidHolder;
import io.nuabo.hikitty.user.application.port.UserProfileDto;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.mock.*;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

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
                "내용입니다.",
                "templateName", "templateValueName", "templateValueCertificationCode");

        TestPasswordEncoderHolder passwordEncoderHolder = new TestPasswordEncoderHolder();
        TestUuidHolder testUuidHolder = new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
        FakeObjectHolder fakeObjectMetadataHolder = new FakeObjectHolder();
        FakeAmazonS3ClientHolder fakeAmazonS3ClientHolder = new FakeAmazonS3ClientHolder("bucket");
        FakeProfileRepository fakeProfileRepository = new FakeProfileRepository();
        TestDefaultImageConfig testDefaultImageConfig = TestDefaultImageConfig.builder()
                .defaultImageFundraiserUrl("defaultImageFundraiserUrl")
                .defaultImageDonerUrl("defaultImageDonerUrl")
                .defaultImageDonerOriginalName("defaultImageDonerOriginalName")
                .defaultImageFundraiserOriginalName("defaultImageFundraiserOriginalName")
                .build();
        this.userService = UserServiceImpl.builder()
                .userRepository(fakeUserRepository)
                .uuidHolder(testUuidHolder)
                .defaultImageConfig(testDefaultImageConfig)
                .certificationService(new CertificationService(fakeMailSender, fakeMailSenderConfig))
                .passwordEncoder(passwordEncoderHolder)
                .profileRepository(fakeProfileRepository)
                .awsConnection(new FakeAwsConnection(testUuidHolder, fakeObjectMetadataHolder, fakeAmazonS3ClientHolder))
                .heartRepository(new FakeHeartRepository())
                .boardRepository(new FakeBoardRepository())
                .build();
        User user = User.builder()
                .id(1L)
                .email("spring2@naver.com")
                .name("kok202")
                .password("1234")
                .role(Role.ROLE_DONER)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        User lastUser = User.builder()
                .id(3L)
                .email("spring3@naver.com")
                .name("푸항항")
                .password("1234")
                .role(Role.ROLE_DONER)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user);
        fakeUserRepository.save(lastUser);
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("spring@naver.com")
                .password("1234")
                .role(Role.ROLE_DONER)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());
        fakeProfileRepository.save(Profile.builder()
                .originalName("testImg.png")
                .savedName("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa.png")
                .url("https://bucket.s3.ap-northeast-2.amazonaws.com/nuabo/aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa.jpg")
                .build(), lastUser);

        User testUser = User.builder()
                .id(5L)
                .email("test@naver.com")
                .name("kok202")
                .password("1234")
                .role(Role.ROLE_DONER)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(testUser);
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
    @DisplayName("userUpdate로 인해 user의 이메일과 패스워드 또는 profile이 변경된다.")
    void update() throws IOException {
        // given
        UserUpdateRequest userUpdate = UserUpdateRequest.builder()
                .name("spring3")
                .password("12345678")
                .build();
        String fileName = "testImg";
        String contentType = "jpg";
        String filePath = "src/test/resources/img/testImg.png";

        String email = "spring2@naver.com";
        MockMultipartFile getMockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);

        // when
        UserProfileDto userProfileDto = userService.update(email, userUpdate, getMockMultipartFile);

        // then
        User result = userService.getByEmail(email);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("spring3");
        assertThat(result.getPassword()).isEqualTo("12345678");
        assertThat(result.getEmail()).isEqualTo("spring2@naver.com");
        assertThat(userProfileDto.getUrl()).isEqualTo("https://bucket.s3.ap-northeast-2.amazonaws.com/nuabo/aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa.jpg");
        assertThat(userProfileDto.getOriginalName()).isEqualTo("testImg.jpg");
    }
    @Test
    @DisplayName("userUpdate로 인해 user의 이메일과 패스워드가 변경된다.")
    void updateNotImg() {
        // given
        UserUpdateRequest userUpdate = UserUpdateRequest.builder()
                .name("spring3")
                .password("12345678")
                .build();
        String email = "spring2@naver.com";
        MockMultipartFile getMockMultipartFile = null;

        // when
        UserProfileDto userProfileDto = userService.update(email, userUpdate, getMockMultipartFile);

        // then
        User result = userService.getByEmail(email);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("spring3");
        assertThat(result.getPassword()).isEqualTo("12345678");
        assertThat(result.getEmail()).isEqualTo("spring2@naver.com");
        assertThat(userProfileDto.getUrl()).isEqualTo(null);
        assertThat(userProfileDto.getOriginalName()).isEqualTo(null);


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

    @Test
    @DisplayName("user가 있고 profile 사진 정보가 없을 시 user 정보만 가져온다.")
    void getUserAndProfileOnlyUser() {
        // given
        String email = "test@naver.com";

        // when
         UserProfileDto userProfileDto = userService.getUserAndProfile(email);

        // then
        assertThat(userProfileDto.getUrl()).isEqualTo("defaultImageDonerUrl");
        assertThat(userProfileDto.getOriginalName()).isEqualTo("defaultImageDonerOriginalName");
        assertThat(userProfileDto.getName()).isEqualTo("kok202");
        assertThat(userProfileDto.getEmail()).isEqualTo("test@naver.com");
    }

    @Test
    @DisplayName("user정보와 profile 사진 정보를 가져온다.")
    void getUserAndProfile() {
        // given
        String email = "spring3@naver.com";

        // when
        UserProfileDto userProfileDto = userService.getUserAndProfile(email);

        // then
        assertThat(userProfileDto.getUrl()).isEqualTo("https://bucket.s3.ap-northeast-2.amazonaws.com/nuabo/aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa.jpg");
        assertThat(userProfileDto.getOriginalName()).isEqualTo("testImg.png");
        assertThat(userProfileDto.getName()).isEqualTo("푸항항");
        assertThat(userProfileDto.getEmail()).isEqualTo("spring3@naver.com");
    }


    @Test
    @DisplayName("이메일 존재 여부 확인 - true")
    void existByEmail() {
        // given
        String email = "spring2@naver.com";

        // when
        boolean result = userService.existsByEmail(email);

        // then
        assertThat(result).isTrue();

    }
    @Test
    @DisplayName("이메일 존재 여부 확인 - false")
    void existByEmailFalse() {
        // given
        String email = "spring@naver.com";

        // when
        boolean result = userService.existsByEmail(email);

        // then
        assertThat(result).isFalse();

    }


    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }
}