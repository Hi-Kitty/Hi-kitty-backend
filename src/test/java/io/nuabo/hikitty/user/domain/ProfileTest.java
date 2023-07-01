package io.nuabo.hikitty.user.domain;

import io.nuabo.hikitty.amazons3.domain.AmazonS3Upload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProfileTest {

    @Test
    @DisplayName("AmazonS3Upload로부터 Profile을 만들어낸다.")
    void from() {

        // given
        User user = User.builder()
                .id(1L)
                .email("spring3@naver.com")
                .role(Role.ROLE_DONER)
                .name("푸항항")
                .password("12354721")
                .status(UserStatus.ACTIVE)
                .certificationCode("bbbbbbb-bbbb-bbbb-bbbbbbbbbbbb")
                .lastLoginAt(100L)
                .build();
        AmazonS3Upload amazonS3Upload = AmazonS3Upload.builder()
                .originalName("푸항항.img")
                .savedName("randomvalue")
                .url("http://amazon")
                .build();

        // when
        Profile profile = Profile.from(amazonS3Upload, user);

        // then
        assertThat(profile.getOriginalName()).isEqualTo("푸항항.img");
        assertThat(profile.getSavedName()).isEqualTo("randomvalue");
        assertThat(profile.getUrl()).isEqualTo("http://amazon");
        assertThat(profile.getUser()).isEqualTo(user);


    }

    @Test
    @DisplayName("새로운 Profile로 업데이트한다.")
    void update() {

            // given
            User user = User.builder()
                    .id(1L)
                    .email("spring3@naver.com")
                    .role(Role.ROLE_DONER)
                    .name("푸항항")
                    .password("12354721")
                    .status(UserStatus.ACTIVE)
                    .certificationCode("bbbbbbb-bbbb-bbbb-bbbbbbbbbbbb")
                    .lastLoginAt(100L)
                    .build();

            Profile profile = Profile.builder()
                    .id(1L)
                    .originalName("푸항항.img")
                    .savedName("randomvalue")
                    .url("http://amazon")
                    .user(user)
                    .build();

            Profile newProfile = Profile.builder()
                    .originalName("푸항항.img")
                    .savedName("randomvalue")
                    .url("http://amazon")
                    .user(user)
                    .build();

            // when
            Profile updatedProfile = profile.update(newProfile, user);

            // then
            assertThat(updatedProfile.getId()).isEqualTo(1L);
            assertThat(updatedProfile.getOriginalName()).isEqualTo("푸항항.img");
            assertThat(updatedProfile.getSavedName()).isEqualTo("randomvalue");
            assertThat(updatedProfile.getUrl()).isEqualTo("http://amazon");
            assertThat(updatedProfile.getUser()).isEqualTo(user);
    }
}