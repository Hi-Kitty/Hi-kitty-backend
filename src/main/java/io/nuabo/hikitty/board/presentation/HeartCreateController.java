package io.nuabo.hikitty.board.presentation;

import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.presentation.port.HeartService;
import io.nuabo.hikitty.board.presentation.response.HeartResponse;
import io.nuabo.hikitty.security.presentation.port.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.nuabo.common.domain.utils.ApiUtils.success;

@Tag(name = "기부자 하트 생성 API")
@SecurityRequirement(name = "Bearer Authentication")
@Builder
@RestController
@RequestMapping("/api/v1/doners")
@RequiredArgsConstructor
public class HeartCreateController {

    private final HeartService heartService;

    private final AuthenticationService authenticationService;

    @Operation(summary = "기부자 하트 생성")
    @PostMapping(value = "/boards/{boardId}/hearts")
    public ResponseEntity<ApiResult<HeartResponse>> create(
           @PathVariable Long boardId
    ) {
        String email = authenticationService.getEmail();
        Heart heart = heartService.createHeart(boardId, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(success(HeartResponse.from(heart)));
    }

    @Operation(summary = "기부자 하트 삭제")
    @DeleteMapping(value = "/boards/hearts/{heartId}")
    public ResponseEntity<ApiResult<HeartResponse>> delete(
           @PathVariable Long heartId
    ) {
        Heart heart = heartService.deleteHeart(heartId);
        return ResponseEntity.ok()
                .body(success(HeartResponse.from(heart)));
    }


}
