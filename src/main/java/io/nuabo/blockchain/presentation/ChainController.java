package io.nuabo.blockchain.presentation;

import io.nuabo.blockchain.application.ChainService;
import io.nuabo.blockchain.infrastructure.ChainResponse;
import io.nuabo.blockchain.presentation.port.ChainCreateRequest;
import io.nuabo.common.domain.utils.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.nuabo.common.domain.utils.ApiUtils.success;
@Slf4j
@Tag(name = "블록체인 API")
@RestController
@RequestMapping("/api/v1/chains")
@RequiredArgsConstructor
public class ChainController {
    private final ChainService chainService;

    @GetMapping("/{key}")
    public ResponseEntity<ApiResult<ChainResponse>> getTransaction(
            @PathVariable String key
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(success(chainService.getTransaction(key)));
    }

    @PostMapping
    public void sendTransaction(
            @RequestBody ChainCreateRequest chainRequest
    ) {
        chainService.sendCreate(chainRequest);
    }
}
