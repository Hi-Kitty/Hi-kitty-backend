package io.nuabo.hikitty.user.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    @Schema(description = "유저 jwt 토큰")
    String token;

    @Builder
    public LoginResponse(String token) {
        this.token = token;
    }
}
