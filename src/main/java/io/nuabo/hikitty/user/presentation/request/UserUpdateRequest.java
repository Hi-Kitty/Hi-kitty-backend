package io.nuabo.hikitty.user.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequest {


    @Schema(description = "name",  example = "푸항항")
    private final String name;

    @Schema(description = "password",  example = "푸항항@naver.com")
    private final String password;


    @Builder
    public UserUpdateRequest(
            @JsonProperty("name") String name,
            @JsonProperty("password") String password
            ) {
        this.name = name;
        this.password = password;

    }
}
