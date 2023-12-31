package io.nuabo.hikitty.user.application.port;

import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {

    User getById(long id);

    Optional<User> findByEmailAndStatus(String email, UserStatus active);

    Optional<User> findByIdAndStatus(long id, UserStatus active);

    User save(User user);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);
    User getByEmailAndStatus(String email, UserStatus active);

    User getByIdAndStatus(Long fundraiserId, UserStatus active);
}
