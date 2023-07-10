package io.nuabo.blockchain.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ChainResponse {

    private final String message;
    private final ChainBody body;

    @Builder
    public ChainResponse(String message, ChainBody body) {
        this.message = message;
        this.body = body;
    }
}
