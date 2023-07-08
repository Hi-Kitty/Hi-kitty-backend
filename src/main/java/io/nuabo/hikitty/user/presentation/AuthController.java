package io.nuabo.hikitty.user.presentation;


import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.security.presentation.port.AuthenticationService;
import io.nuabo.hikitty.user.application.port.UserProfileDto;
import io.nuabo.hikitty.user.presentation.port.UserService;
import io.nuabo.hikitty.user.presentation.request.UserUpdateRequest;
import io.nuabo.hikitty.user.presentation.response.UserAndProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "인증 인가 - 기부자 모금자 공통 API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationService authenticationService;


    @Operation(summary = "유저 정보 조회 with email", description = "이미지와 함께 출력됩니다.")
    @GetMapping("/users")
    public ResponseEntity<ApiResult<UserAndProfileResponse>> getByEmail() {
        String email = authenticationService.getEmail();
        UserProfileDto userAndProfile = userService.getUserAndProfile(email);

        return ResponseEntity.ok(ApiUtils.success(UserAndProfileResponse.from(userAndProfile)));
    }


    @Operation(summary = "유저 정보 수정", description = "토큰 값을 입력하고 이미지, 닉네임 비밀번호를 입력하세요.")
    @PutMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResult<UserAndProfileResponse>> updatePassword(
            @Valid @RequestPart(required = false) UserUpdateRequest request,
            @RequestPart(value = "img", required = false) MultipartFile img
    ) {
        String email = authenticationService.getEmail();
        UserProfileDto update = userService.update(email, request, img);
        return ResponseEntity.ok(ApiUtils.success(UserAndProfileResponse.from(update)));
    }
}
