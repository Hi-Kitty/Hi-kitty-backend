package io.nuabo.hikitty.user.domain;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Profile {
    private final Long id;
    private final String originalName;
    private final String savedName;
    private final String url;

    private final User user;

    @Builder
    public Profile(Long id, String originalName, String savedName, String url, User user) {
        this.id = id;
        this.originalName = originalName;
        this.savedName = savedName;
        this.url = url;
        this.user = user;
    }

    public static Profile from(AmazonS3Upload amazonS3Upload, User user) {
        return Profile.builder()
                .originalName(amazonS3Upload.getOriginalName())
                .savedName(amazonS3Upload.getSavedName())
                .url(amazonS3Upload.getUrl())
                .user(user)
                .build();
    }

    public Profile update(Profile newProfile, User user) {
        return Profile.builder()
                .id(id)
                .originalName(newProfile.getOriginalName())
                .savedName(newProfile.getSavedName())
                .url(newProfile.getUrl())
                .user(user)
                .build();
    }
}
