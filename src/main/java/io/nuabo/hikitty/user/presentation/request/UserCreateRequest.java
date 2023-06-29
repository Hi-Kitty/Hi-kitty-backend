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
    @Schema(description = "이메일",  example = "33cks1423@naver.com")
    private final String email;

    @NotNull(message = "name cannot be null")
    @Schema(description = "name",  example = "푸항항")
    private final String name;

    @NotNull(message = "password cannot be null")
    @Schema(description = "password",  example = "12354721")
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
