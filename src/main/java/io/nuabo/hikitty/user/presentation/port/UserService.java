package io.nuabo.hikitty.user.presentation.port;

import io.nuabo.hikitty.user.application.port.UserProfileDto;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User create(UserCreateRequest userCreateRequest);

    User getByEmail(String email);

    User getById(long id);

    UserProfileDto update(String email, UserUpdateRequest userUpdate, MultipartFile img);

    void verifyEmail(long id, String certificationCode);

    UserProfileDto getUserAndProfile(String email);
}
