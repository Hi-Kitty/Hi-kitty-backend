package io.nuabo.hikitty.user.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequest {


    @Size(min=1, max=255, message = "이름은 1자 이상 255자 이하로 입력해주세요.")
    @Schema(description = "name",  example = "푸항항")
    private final String name;


    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "패스워드는 공백 없이, 숫자, 대문자, 소문자가 하나씩 포함되어야 합니다. (8글자 이상)")
    @Schema(description = "비밀번호",  example = "1234!Aa1234")
    @Size(min=1, max=255, message = "비밀번호는 1자 이상 255자 이하로 입력해주세요.")
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
