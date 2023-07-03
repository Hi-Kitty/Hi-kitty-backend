package io.nuabo.hikitty.user.domain;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.application.port.UuidHolder;

import io.nuabo.common.domain.exception.CertificationCodeNotMatchedException;
import io.nuabo.hikitty.security.application.port.PasswordEncoderHolder;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private final Long id;
    private final String email;
    private final String name;
    private final String certificationCode;
    private final UserStatus status;
    private final Long lastLoginAt;

    private final Role role;

    private final String password;

    @Builder
    public User(Long id, String email, String name, String certificationCode, UserStatus status, Long lastLoginAt, Role role, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.certificationCode = certificationCode;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
        this.role = role;
        this.password = password;
    }

    public static User from(UserCreateRequest userCreateRequest, UuidHolder uuidHolder) {
        return User.builder()
                .email(userCreateRequest.getEmail())
                .name(userCreateRequest.getName())
                .role(userCreateRequest.getRole())
                .status(UserStatus.PENDING)
                .certificationCode(uuidHolder.randomNumber())
                .password(userCreateRequest.getPassword())
                .build();
    }

    public User check(Long beforeId, UuidHolder uuidHolder) {
            return User.builder()
                    .id(beforeId)
                    .email(email)
                    .name(name)
                    .role(role)
                    .status(UserStatus.PENDING)
                    .certificationCode(uuidHolder.randomNumber())
                    .password(password)
                    .build();
    }

    public User certificate(String certificationCode, String code) {
        if (!certificationCode.equals(code)) {
            throw new CertificationCodeNotMatchedException();
        }
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .certificationCode(certificationCode)
                .status(UserStatus.ACTIVE)
                .lastLoginAt(lastLoginAt)
                .role(role)
                .password(password)
                .build();
    }

    public static User register(UserCreateRequest userCreateRequest, PasswordEncoderHolder passwordEncoder, UuidHolder uuidHolder) {
        return User.builder()
                .email(userCreateRequest.getEmail())
                .name(userCreateRequest.getName())
                .role(userCreateRequest.getRole())
                .status(UserStatus.PENDING)
                .certificationCode(uuidHolder.randomNumber())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .build();
    }

    public User login(ClockHolder clockHolder) {
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .certificationCode(certificationCode)
                .status(status)
                .lastLoginAt(clockHolder.millis())
                .role(role)
                .password(password)
                .build();
    }

    public User update(UserUpdateRequest userUpdate, PasswordEncoderHolder passwordEncoder) {
        return User.builder()
                .id(id)
                .email(email)
                .name(userUpdate.getName() == null ? name : userUpdate.getName())
                .certificationCode(certificationCode)
                .status(status)
                .lastLoginAt(lastLoginAt)
                .role(role)
                .password(userUpdate.getPassword() == null ? password : passwordEncoder.encode(userUpdate.getPassword()))
                .build();
    }
}
