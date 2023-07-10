package io.nuabo.blockchain.presentation.port;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChainCreateRequest {

    private final String key;
    private final String timestamp;
    private final Long amount;
    private final String donorName;
    private final String fundraiserName;

    @Builder
    public ChainCreateRequest(
            @JsonProperty String key,
            @JsonProperty String timestamp,
            @JsonProperty Long amount,
            @JsonProperty String donorName,
            @JsonProperty String fundraiserName) {
        this.key = key;
        this.timestamp = timestamp;
        this.amount = amount;
        this.donorName = donorName;
        this.fundraiserName = fundraiserName;
    }
}
