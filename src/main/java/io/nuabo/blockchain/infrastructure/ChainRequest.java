package io.nuabo.blockchain.infrastructure;

import io.nuabo.blockchain.presentation.port.ChainCreateRequest;
import io.nuabo.hikitty.toss.presentation.response.CompleteResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChainRequest {

    private final String key;
    private final String timestamp;
    private final Long amount;
    private final String donorName;
    private final String fundraiserName;

    @Builder
    public ChainRequest(String key, String timestamp, Long amount, String donorName, String fundraiserName) {
        this.key = key;
        this.timestamp = timestamp;
        this.amount = amount;
        this.donorName = donorName;
        this.fundraiserName = fundraiserName;
    }

    public static ChainRequest from(CompleteResponse response) {
        return ChainRequest.builder()
                .key(response.getOrderId())
                .amount(response.getAmount())
                .donorName(response.getCustomerName())
                .fundraiserName(response.getFundraiserName())
                .timestamp(response.getCreatedAt().toString())
                .build();
    }

    public static ChainRequest from(ChainCreateRequest chainCreateRequest) {
        return ChainRequest.builder()
                .key(chainCreateRequest.getKey())
                .amount(chainCreateRequest.getAmount())
                .donorName(chainCreateRequest.getDonorName())
                .fundraiserName(chainCreateRequest.getFundraiserName())
                .timestamp(chainCreateRequest.getTimestamp())
                .build();
    }
}
