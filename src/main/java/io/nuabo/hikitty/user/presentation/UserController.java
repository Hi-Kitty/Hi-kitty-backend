package io.nuabo.hikitty.user.presentation;

import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.presentation.port.UserService;
import io.nuabo.hikitty.user.presentation.request.LoginRequest;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import io.nuabo.hikitty.user.presentation.request.UserCreateRequest;
import io.nuabo.hikitty.user.presentation.response.LoginResponse;
import io.nuabo.hikitty.user.presentation.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

import static io.nuabo.common.domain.utils.ApiUtils.success;


@Tag(name = "유저 - 기부자, 모금자 보완 관련 통합 API")
@Builder
@RestController
@RequestMapping("/api/v0/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
                .location(URI.create("http://localhost:3000"))
                .build();
    }

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
        return null;
    }

    // security 필요
    @Operation(summary = "유저 정보 수정", description = "토큰 값을 입력하고 이미지, 닉네임 비밀번호를 입력하세요.")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<UserUpdateRequest>> updatePassword(
            @RequestPart UserUpdateRequest request,
            @RequestPart(value = "img", required = false) MultipartFile img
    ) {
        return null;
    }
}
