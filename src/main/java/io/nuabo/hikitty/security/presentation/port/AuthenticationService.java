package io.nuabo.hikitty.security.presentation.port;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.presentation.request.LoginRequest;

public interface AuthenticationService {

    String authenticate(LoginRequest request);

    String getEmail();

    User getUser();
}
