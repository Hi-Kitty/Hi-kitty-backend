package io.nuabo.hikitty.board.application;

import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.application.port.HeartRepository;
import io.nuabo.hikitty.board.mock.FakeBoardRepository;
import io.nuabo.hikitty.board.mock.FakeHeartRepository;
import io.nuabo.hikitty.board.presentation.port.BoardService;
import io.nuabo.hikitty.board.presentation.request.BoardCreateRequest;
import io.nuabo.hikitty.board.presentation.request.PlanCreateRequest;
import io.nuabo.hikitty.mock.TestUuidHolder;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.mock.FakeProfileRepository;
import io.nuabo.hikitty.user.mock.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

class BoardServiceTest {

    private BoardService boardService;

    @BeforeEach
    void init() {

        BoardRepository boardRepository = new FakeBoardRepository();
        HeartRepository heartRepository = new FakeHeartRepository();
        UserRepository userRepository = new FakeUserRepository();
        ProfileRepository profileRepository = new FakeProfileRepository();
        TestUuidHolder testUuidHolder = new TestUuidHolder("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

//        new FakeAwsConnection()
        User testUser = User.builder()
                .email("이메일@gmail.com")
                .name("kok202")
                .password("1234")
                .role(Role.ROLE_DONER)
                .certificationCode("aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        userRepository.save(testUser);

        boardService = BoardServiceImpl.builder()
                .boardRepository(boardRepository)
                .heartRepository(heartRepository)
                .userRepository(userRepository)
                .profileRepository(profileRepository)
                .build();


    }

    @Test
    @DisplayName("유저가 존재하고 파일이 존재하고 플랜이 존재할 때 정상적으로 생성되는지 확인")
    void create() throws IOException {
        // given
        String email = "이메일@gmail.com";
        String fileName = "testImg";
        String contentType = "jpg";
        String filePath = "src/test/resources/img/testImg.png";
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .title("제목")
                .content("컨텐트")
                .subTitle("서브타이틀")
                .targetAmount(100000L)
                .endAt(LocalDate.parse("2021-08-01"))
                .build();
        MockMultipartFile multipartFile = getMockMultipartFile(fileName, contentType, filePath);
        PlanCreateRequest planCreateRequest = PlanCreateRequest.builder()
                .reason("플랜 컨텐트")
                .amount(10000L)
                .build();
        List<PlanCreateRequest> planCreateRequests = List.of(planCreateRequest);
//        BoardFundraiserImagePlan result = boardService.create(boardCreateRequest, email, multipartFile, planCreateRequests);



    }

    @Test
    void getPages() {
    }

    @Test
    void getById() {
    }

    @Test
    void getPagesByFundraiserEmail() {
    }

    @Test
    void getPagesByFundraiserId() {
    }

    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }
}