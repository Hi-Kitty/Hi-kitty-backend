package io.nuabo.hikitty.toss.presentation;
import io.nuabo.common.domain.utils.ApiUtils;
import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.nuabo.hikitty.security.presentation.port.AuthenticationService;
import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.presentation.port.PaymentService;
import io.nuabo.hikitty.toss.presentation.request.PaymentFailRequest;
import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;
import io.nuabo.hikitty.toss.presentation.request.OrderRequest;
import io.nuabo.hikitty.toss.presentation.response.OrderResponse;
import io.nuabo.hikitty.toss.presentation.response.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Tag(name = "결제 시스템 API")
@SecurityRequirement(name = "Bearer Authentication")
@Builder
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "결제 요청", description = "기부하기 버튼을 누르면 해당 API를 전송해서 응답 값을 받으세요.")
    @PostMapping
    public ResponseEntity<ApiResult<OrderResponse>> request(
            @Valid @RequestBody OrderRequest request
    ) {
        String email = authenticationService.getEmail();
        return ResponseEntity.ok(ApiUtils.success(paymentService.request(request, email)));
    }

    @Operation(summary = "결제 성공 리다이렉트", description = "결제 성공 시 최종 결제 승인 요청을 보낸다.")
    @GetMapping("/success")
    public ResponseEntity<ApiResult<PaymentResponse>> success(
            @Valid @ModelAttribute PaymentQueryRequest request
            ) {
        Payment payment = paymentService.process(request);

        return ResponseEntity.ok(ApiUtils.success(paymentService.increaseBoard(payment)));
    }

    @Operation(summary = "결제 실패 리다이렉트", description = "결제 실패 시 최종 결제 승인 요청을 보낸다.")
    @GetMapping("/fail")
    public ResponseEntity<ApiResult<PaymentFailRequest>> fail(
            @Valid @ModelAttribute PaymentFailRequest request
            ) {
        paymentService.fail(request);
        return ResponseEntity.ok(ApiUtils.success(request));
    }
}