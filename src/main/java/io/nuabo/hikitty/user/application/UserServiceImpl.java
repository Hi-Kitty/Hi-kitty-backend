package io.nuabo.hikitty.user.application;

import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.presentation.port.UserService;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Builder
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UuidHolder uuidHolder;

    private final CertificationService certificationService;

    @Transactional
    @Override
    public User create(UserCreateRequest userCreateRequest) {
        User user = User.from(userCreateRequest, uuidHolder);

        Optional<User> beforeUser = userRepository.findByEmail(userCreateRequest.getEmail());
        if (beforeUser.isPresent()) {
            user = user.check(beforeUser.get().getId(), uuidHolder);
        }

        user = userRepository.save(user);
        certificationService.send(userCreateRequest.getEmail(), user.getId(), user.getCertificationCode());
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public User update(long id, UserUpdateRequest userUpdate) {
        return null;
    }

    @Transactional
    @Override
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.certificate(certificationCode, user.getCertificationCode());
        userRepository.save(user);
    }
}
