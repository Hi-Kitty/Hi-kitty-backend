package io.nuabo.hikitty.user.mock;
import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.hikitty.user.application.CertificationService;
import io.nuabo.hikitty.user.application.UserServiceImpl;
import io.nuabo.hikitty.user.application.port.MailSender;
import io.nuabo.hikitty.user.application.port.MailSenderConfig;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.presentation.UserController;
import io.nuabo.hikitty.user.presentation.port.UserService;
import lombok.Builder;

public class TestUserContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final CertificationService certificationService;
    public final UserController userController;
    public final UserService userService;

    public final MailSenderConfig mailSenderConfig;

    @Builder
    public TestUserContainer(
            ClockHolder clockHolder,
            UuidHolder uuidHolder,
            MailSenderConfig mailSenderConfig

    ) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.mailSenderConfig = mailSenderConfig;
        this.certificationService = new CertificationService(
                this.mailSender,
                this.mailSenderConfig
        );

        UserServiceImpl userService = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .userRepository(this.userRepository)
                .certificationService(this.certificationService)
                .passwordEncoder(new TestPasswordEncoderHolder())
                .build();

        this.userService = userService;
        this.userController = UserController.builder()
                .userService(userService)
                .redirectUrlConfig(new TestRedirectUrlConfig("http://localhost:3030/"))
                .build();

    }
}
