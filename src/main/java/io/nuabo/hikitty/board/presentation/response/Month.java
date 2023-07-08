package io.nuabo.hikitty.board.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Month {
    private final String month;
    private final Long amount;
    private final Long count;

    @Builder
    public Month(String month, Long amount, Long count) {
        this.month = month;
        this.amount = amount;
        this.count = count;
    }
}
