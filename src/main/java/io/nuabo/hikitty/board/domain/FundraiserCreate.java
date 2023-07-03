package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class FundraiserCreate {

    private final Long id;

    private final String name;

    private final String profileName;

    private final String profileUrl;

    private final LocalDateTime createdAt;

    @Builder
    public FundraiserCreate(Long id, String name, String profileName, String profileUrl, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.profileName = profileName;
        this.profileUrl = profileUrl;
        this.createdAt = createdAt;
    }

    public static FundraiserCreate from(User user, Profile profile) {
        return FundraiserCreate.builder()
                .id(user.getId())
                .name(user.getName())
                .profileName(profile.getOriginalName())
                .profileUrl(profile.getUrl())
                .build();
    }

    public static FundraiserCreate from(User user, String imageBasename, String imageBaseUrl) {
        return FundraiserCreate.builder()
                .id(user.getId())
                .name(user.getName())
                .profileName(imageBasename)
                .profileUrl(imageBaseUrl)
                .build();
    }
}
