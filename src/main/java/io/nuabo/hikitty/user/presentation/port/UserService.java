package io.nuabo.hikitty.user.presentation.port;

import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;

public interface UserService {
    User create(UserCreateRequest userCreateRequest);

    User getByEmail(String email);

    User getById(long id);

    User update(long id, UserUpdateRequest userUpdate);

    void verifyEmail(long id, String certificationCode);
}
