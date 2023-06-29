package io.nuabo.hikitty.user.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotNull(message = "email cannot be null")
    @Schema(description = "이메일",  example = "test@naver.com")
    private final String email;

    @NotNull(message = "password cannot be null")
    @Schema(description = "비밀번호",  example = "12345678")
    private final String password;

    @Builder
    public LoginRequest(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
