package io.nuabo.hikitty.user.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.nuabo.hikitty.user.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequest {


    @NotNull(message = "email cannot be null")
    @Schema(description = "이메일",  example = "푸하하@naver.com")
    private final String email;

    @NotNull(message = "nickname cannot be null")
    @Schema(description = "nickname",  example = "푸항항")
    private final String nickname;

    @NotNull(message = "password cannot be null")
    @Schema(description = "password",  example = "12354721")
    private final String password;

    @NotNull(message = "ROLE cannot be null")
    @Schema(description = "ROLE_DONER, ROLE_FUNDRAISER",  example = "ROLE_DONER, ROLE_FUNDRAISER 중 하나를 입력하세요", defaultValue = "ROLE_DONER")
    private final Role role;

    @Builder
    public UserCreateRequest(
            @JsonProperty("email") String email,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("password") String password,
            @JsonProperty("role") Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}
