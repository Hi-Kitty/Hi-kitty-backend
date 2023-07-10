package io.nuabo.blockchain.application;

import io.nuabo.blockchain.infrastructure.port.ChainClient;
import io.nuabo.blockchain.infrastructure.ChainRequest;
import io.nuabo.blockchain.infrastructure.ChainResponse;
import io.nuabo.blockchain.presentation.port.ChainCreateRequest;
import io.nuabo.hikitty.toss.presentation.response.CompleteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChainService {
    private final ChainClient chainClient;

    @Async
    public void sendTransaction(CompleteResponse response) {
        ChainRequest chainRequest = ChainRequest.from(response);
        chainClient.send(chainRequest);
    }

    @Transactional(readOnly = true)
    public ChainResponse getTransaction(String key) {
        ResponseEntity<ChainResponse> responseEntity = chainClient.get(key);
        return responseEntity.getBody();
    }

    @Async
    public void sendCreate(ChainCreateRequest chainCreateRequest) {
        ChainRequest chainRequest = ChainRequest.from(chainCreateRequest);
        chainClient.send(chainRequest);
    }
}
