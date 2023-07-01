package io.nuabo.hikitty.user.application.port;

import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;

import java.util.Optional;

public interface ProfileRepository {
    /**
     * 1. 저장
     * 2. 가져오기
     */
    Profile save(Profile profile, User user);

    Optional<Profile> findById(long id);

    Profile getById(long id);

    Optional<Profile> findByUserId(long userId);

    Profile getByUserId(long userId);

}
