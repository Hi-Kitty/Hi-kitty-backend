package io.nuabo.hikitty.user.presentation.response;


import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    @Schema(description = "유저 식별자")
    private final Long id;

    @Schema(description = "유저 이메일")
    private final String email;

    @Schema(description = "유저 이름")
    private final String name;

    @Schema(description = "유저 역할")
    private final Role role;

    @Schema(description = "유저 상태 - 이메일 인증 여부")
    private final UserStatus status;

    @Builder
    public UserResponse(Long id, String email, String name, Role role, UserStatus status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}
