package io.nuabo.hikitty.user.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.infrastructure.entity.UserEntity;
import io.nuabo.hikitty.user.infrastructure.port.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("users", id));
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus active) {
        return userJpaRepository.findByEmailAndStatus(email, active).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return userJpaRepository.findByIdAndStatus(id, userStatus).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }

    @Override
    public Optional<User> findById(long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toModel);
    }

    @Override
    public User getByEmailAndStatus(String email, UserStatus active) {
        return findByEmailAndStatus(email, active).orElseThrow(() -> new ResourceNotFoundException("users", email));
    }

    @Override
    public User getByIdAndStatus(Long fundraiserId, UserStatus active) {
        return findByIdAndStatus(fundraiserId, active).orElseThrow(() -> new ResourceNotFoundException("users", fundraiserId));
    }
}
