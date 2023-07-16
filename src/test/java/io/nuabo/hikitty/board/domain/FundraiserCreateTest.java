package io.nuabo.hikitty.board.domain;

import io.nuabo.hikitty.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FundraiserCreateTest {

    @Test
    @DisplayName("User를 이용하여 FundraiserCreate를 생성한다.")
    void from() {
        // given
        User user = User.builder()
                .id(1L)
                .name("이름")
                .build();

        // when
        FundraiserCreate fundraiserCreate = FundraiserCreate.from(user);

        // then
        assertAll(
                () -> assertEquals(fundraiserCreate.getId(), 1L),
                () -> assertEquals(fundraiserCreate.getName(), "이름")
        );
    }

    @Test
    @DisplayName("User와 이미지 정보를 이용하여 FundraiserCreate를 생성한다.")
    void testFrom() {
        // given
        User user = User.builder()
                .id(1L)
                .name("이름")
                .build();

        // when
        FundraiserCreate fundraiserCreate = FundraiserCreate.from(user, "basename", "baseUrl");

        // then
        assertAll(
                () -> assertEquals(fundraiserCreate.getId(), 1L),
                () -> assertEquals(fundraiserCreate.getName(), "이름"),
                () -> assertEquals(fundraiserCreate.getProfileName(), "basename"),
                () -> assertEquals(fundraiserCreate.getProfileUrl(), "baseUrl")
        );

    }
}