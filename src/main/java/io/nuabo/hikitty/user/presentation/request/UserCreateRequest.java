package io.nuabo.hikitty.user.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.nuabo.hikitty.user.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequest {


    @NotNull(message = "email cannot be null")
    @Email
    @Schema(description = "이메일",  example = "33cks1423@naver.com")
    @Size(min=1, max=255, message = "email의 크키가 1에서 255 사이어야 합니다.")
    private final String email;

    @NotNull(message = "name cannot be null")
    @Size(min=1, max=255, message = "name의 크키가 1에서 255 사이어야 합니다.")
    @Schema(description = "name",  example = "푸항항")
    private final String name;

    @NotNull(message = "password cannot be null")
    @Size(min=1, max=255, message = "password의 크키가 1에서 255 사이어야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "패스워드는 공백 없이, 숫자, 대문자, 소문자가 하나씩 포함되어야 합니다. (8글자 이상)")
    @Schema(description = "비밀번호",  example = "1234!Aa1234")
    private final String password;

    @NotNull(message = "ROLE cannot be null")
    @Schema(description = "ROLE_DONER, ROLE_FUNDRAISER",  example = "ROLE_DONER, ROLE_FUNDRAISER 중 하나를 입력하세요", defaultValue = "ROLE_DONER")
    private final Role role;

    @Builder
    public UserCreateRequest(
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("password") String password,
            @JsonProperty("role") Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
