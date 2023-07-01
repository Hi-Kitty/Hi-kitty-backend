package io.nuabo.hikitty.user.infrastructure.port;

import io.nuabo.hikitty.user.infrastructure.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByUserId(long userId);
}
