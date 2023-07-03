package io.nuabo.hikitty.user.application.port;

import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.Role;
import io.nuabo.hikitty.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileDto {

    private final Long id;

    private final String email;

    private final String name;

    private final Role role;

    private final String originalName;

    private final String url;

    @Builder
    public UserProfileDto(Long id, String email, String name, Role role, String originalName, String url) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.originalName = originalName;
        this.url = url;
    }

    public static UserProfileDto merge(Profile profile, User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .originalName(profile.getOriginalName())
                .url(profile.getUrl())
                .build();
    }

    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public static Profile merge(User user, String defaultImageUrl, String defaultImageOriginalName) {
        return Profile.builder()
                .originalName(defaultImageOriginalName)
                .savedName(defaultImageUrl)
                .url(defaultImageUrl)
                .user(user)
                .build();
    }
}
