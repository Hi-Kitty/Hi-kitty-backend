package io.nuabo.hikitty.security.application;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.domain.exception.MyUsernameNotFoundException;
import io.nuabo.hikitty.security.presentation.port.AuthenticationService;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import io.nuabo.hikitty.user.infrastructure.entity.UserEntity;

import io.nuabo.hikitty.user.presentation.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ClockHolder clockHolder;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public String authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmailAndStatus(request.getEmail(), UserStatus.ACTIVE)
                .orElseThrow(() -> new MyUsernameNotFoundException(request.getEmail()));

        user = user.login(clockHolder);
        user = userRepository.save(user);

        return jwtService.generateToken(UserEntity.from(user));
    }

    @Transactional
    @Override
    public String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Transactional
    @Override
    public User getUser() {
        String email = getEmail();
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new MyUsernameNotFoundException(email));
    }
}
