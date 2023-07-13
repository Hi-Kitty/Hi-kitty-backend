package io.nuabo.hikitty.user.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.user.application.port.ProfileRepository;
import io.nuabo.hikitty.user.domain.Profile;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.infrastructure.entity.ProfileEntity;
import io.nuabo.hikitty.user.infrastructure.port.ProfileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileJpaRepository profileJpaRepository;


    @Override
    public Profile save(Profile profile, User user) {
        return profileJpaRepository.save(ProfileEntity.from(profile, user)).toModel();
    }

    @Override
    public Profile save(Profile profile) {
        return null;
    }

    @Override
    public Optional<Profile> findById(long id) {
        return profileJpaRepository.findById(id).map(ProfileEntity::toModel);
    }

    @Override
    public Profile getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("profile", id));
    }

    @Override
    public Optional<Profile> findByUserId(long userId) {
        return profileJpaRepository.findByUserId(userId).map(ProfileEntity::toModel);
    }

    @Override
    public Profile getByUserId(long userId) {
        return findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("profile", userId));
    }


}
