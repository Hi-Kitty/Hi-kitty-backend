package io.nuabo.hikitty.user.infrastructure;

import io.nuabo.hikitty.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
