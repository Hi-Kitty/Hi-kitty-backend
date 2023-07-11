package io.nuabo.blockchain.infrastructure.port;

import io.nuabo.blockchain.infrastructure.ChainMessageResponse;
import io.nuabo.blockchain.infrastructure.ChainRequest;
import io.nuabo.blockchain.infrastructure.ChainResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "client", url = "${block.chain.url}")
public interface ChainClient {

    @PostMapping
    ResponseEntity<ChainMessageResponse> send(
            @RequestBody ChainRequest chainRequest
    );

    @GetMapping(value = "/{key}")
    ResponseEntity<ChainResponse> get(
            @PathVariable("key") String key
    );
}