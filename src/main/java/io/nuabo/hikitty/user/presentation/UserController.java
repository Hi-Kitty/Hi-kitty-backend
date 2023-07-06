package io.nuabo.hikitty.user.presentation;

import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.common.presentation.port.RedirectUrlConfig;
import io.nuabo.hikitty.security.application.AuthenticationServiceImpl;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.presentation.port.UserService;
import io.nuabo.hikitty.user.presentation.request.EmailRequest;
import io.nuabo.hikitty.user.presentation.request.LoginRequest;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.response.LoginResponse;
import io.nuabo.hikitty.user.presentation.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static io.nuabo.common.domain.utils.ApiUtils.success;


@Tag(name = "유저 - 기부자, 모금자 보완 관련 통합 API")
@Builder
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    private final RedirectUrlConfig redirectUrlConfig;
    @Operation(summary = "회원가입")
    @PostMapping
    public ResponseEntity<ApiResult<UserResponse>> create(@Valid @RequestBody UserCreateRequest userCreate) {
        User user = userService.create(userCreate);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(UserResponse.from(user)));
    }

    @Operation(summary = "이메일 인증", description = "id 값과 code를 입력하세요")
    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable long id,
            @RequestParam String certificationCode) {
        userService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrlConfig.getUserSave())).build();
    }

    @Operation(summary = "유저 정보 조회", description = "id 값을 입력하세요")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<UserResponse>> getById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(success(UserResponse.from(userService.getById(id))));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력하세요")
    @PostMapping("/login")
    public ResponseEntity<ApiResult<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        String token = authenticationServiceImpl.authenticate(request);
        return ResponseEntity.ok(success(LoginResponse.from(token)));
    }


    @Operation(summary = "기존 회원 확인", description = "이메일을 통해서 기존 회원 여부")
    @GetMapping("/exists")
    public ResponseEntity<ApiResult<Boolean>> existsByEmail(
           @Valid @ModelAttribute EmailRequest emailRequest
            ) {
        return ResponseEntity.ok(success(userService.existsByEmail(emailRequest.getEmail())));
    }

}
