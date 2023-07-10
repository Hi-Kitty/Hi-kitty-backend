package io.nuabo.blockchain.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ChainBody {
    private final String timestamp;
    private final String amount;
    private final String donorName;
    private final String fundraiserName;

    @Builder
    public ChainBody(String timestamp, String amount, String donorName, String fundraiserName) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.donorName = donorName;
        this.fundraiserName = fundraiserName;
    }

}
