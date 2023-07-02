package io.nuabo.hikitty.user.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotNull(message = "email cannot be null")
    @Size(min=1, max=255, message = "이메일은 1자 이상 255자 이하로 입력해주세요.")
    @Email
    @Schema(description = "이메일",  example = "test@naver.com")
    private final String email;

    @NotNull(message = "password cannot be null")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "패스워드는 공백 없이, 숫자, 대문자, 소문자가 하나씩 포함되어야 합니다. (8글자 이상)")
    @Schema(description = "비밀번호",  example = "1234!Aa1234")
    @Size(min=1, max=255, message = "비밀번호는 1자 이상 255자 이하로 입력해주세요.")
    private final String password;

    @Builder
    public LoginRequest(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
