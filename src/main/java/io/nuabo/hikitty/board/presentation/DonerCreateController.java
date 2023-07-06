package io.nuabo.hikitty.board.presentation;

import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.board.domain.Heart;
import io.nuabo.hikitty.board.presentation.port.HeartService;
import io.nuabo.hikitty.board.presentation.response.HeartResponse;
import io.nuabo.hikitty.security.presentation.port.AuthenticationService;
import io.nuabo.hikitty.toss.presentation.port.PaymentService;
import io.nuabo.hikitty.toss.presentation.response.TotalAmountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.nuabo.common.domain.utils.ApiUtils.success;

@Tag(name = "기부자용 - 하트 생성 및 프로필 관련 API")
@SecurityRequirement(name = "Bearer Authentication")
@Builder
@RestController
@RequestMapping("/api/v1/doners")
@RequiredArgsConstructor
public class DonerCreateController {

    private final HeartService heartService;

    private final AuthenticationService authenticationService;

    private final PaymentService paymentService;

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

    @Operation(summary= "총 후원", description = "기부자가 후원한 총 금액과 후원 횟수")
    @GetMapping(value = "/totals")
    public ResponseEntity<ApiResult<TotalAmountResponse>> getTotals() {
        String email = authenticationService.getEmail();
        return ResponseEntity.ok()
                .body(success(paymentService.getByEmail(email)));
    }

}
