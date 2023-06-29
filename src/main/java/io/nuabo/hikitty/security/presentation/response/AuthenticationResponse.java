package io.nuabo.hikitty.security.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {

    @Schema(description = "토큰",  example = "푸항항!!!")
    private String token;
}
