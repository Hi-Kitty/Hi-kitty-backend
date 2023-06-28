package io.nuabo.hikitty.common.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "헬스 체크")
@RestController
public class HealthCheckController {

    @Operation(summary = "헬스 체킹 API")
    @GetMapping("/health_check.html")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity
                .ok()
                .build();
    }
}