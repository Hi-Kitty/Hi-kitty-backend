package io.nuabo.blockchain.infrastructure;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChainMessageResponse {
    private final String message;

    @Builder
    public ChainMessageResponse(String message) {
        this.message = message;
    }
}
