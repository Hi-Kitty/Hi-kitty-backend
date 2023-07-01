package io.nuabo.hikitty.user.infrastructure.port;

import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
