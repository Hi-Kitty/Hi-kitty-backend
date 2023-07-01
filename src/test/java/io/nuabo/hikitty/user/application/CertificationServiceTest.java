package io.nuabo.hikitty.user.application;

import io.nuabo.hikitty.user.mock.FakeMailSender;
import io.nuabo.hikitty.user.mock.FakeMailSenderConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CertificationServiceTest {

    @Test
    @DisplayName("이메일과 컨텐츠가 제대로 만들어져서 보내지는지 테스트한다.")
    void send() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeMailSenderConfig fakeMailSenderConfig = new FakeMailSenderConfig(
                "http://localhost:8080/api/v0/users/",
                "/verify?certificationCode=",
                "제목입니다.",
                "내용입니다: ",
                "templateName",
                "templateValueName",
                "templateValueCertificationCode"
                );
        CertificationService certificationService = new CertificationService(fakeMailSender, fakeMailSenderConfig);

        // when
        certificationService.send("spring2@naver.com", 1L, "aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then
        assertThat(fakeMailSender.email).isEqualTo("spring2@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("제목입니다.");
        assertThat(fakeMailSender.content).isEqualTo("내용입니다: " +
                "aaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }

    @Test
    @DisplayName("타임리프를 이용하여 이메일과 컨텐츠가 작성되는지 확인한다.")
    void sendMailFromTemplate() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeMailSenderConfig fakeMailSenderConfig = new FakeMailSenderConfig(
                "http://localhost:8080/api/v0/users/",
                "/verify?certificationCode=",
                "제목입니다.",
                "내용입니다: ",
                "welcome",
                "name",
                "certificationCode"
        );
        CertificationService certificationService = new CertificationService(fakeMailSender, fakeMailSenderConfig);

        // when
        certificationService.sendMailFromTemplate("spring2@naver.com", "1278872", "template.html");

        //
        assertAll(
                ()-> assertThat(fakeMailSender.email).isEqualTo("spring2@naver.com"),
                () -> assertThat(fakeMailSender.title).isEqualTo("제목입니다."),
                () -> assertThat(fakeMailSender.content).isEqualTo("template.html1278872")
        );
    }


}