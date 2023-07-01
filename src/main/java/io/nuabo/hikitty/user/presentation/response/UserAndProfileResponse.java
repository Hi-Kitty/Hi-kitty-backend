package io.nuabo.hikitty.user.presentation.response;

import io.nuabo.hikitty.user.application.port.UserProfileDto;
import io.nuabo.hikitty.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAndProfileResponse {
    private final Long id;

    private final String email;

    private final String name;

    private final Role role;

    private final String originalName;

    private final String url;

    @Builder
    public UserAndProfileResponse(Long id, String email, String name, Role role, String originalName, String url) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.originalName = originalName;
        this.url = url;
    }

    public static UserAndProfileResponse from(UserProfileDto userAndProfile) {
        return UserAndProfileResponse.builder()
                .id(userAndProfile.getId())
                .email(userAndProfile.getEmail())
                .name(userAndProfile.getName())
                .role(userAndProfile.getRole())
                .originalName(userAndProfile.getOriginalName())
                .url(userAndProfile.getUrl())
                .build();
    }
}
