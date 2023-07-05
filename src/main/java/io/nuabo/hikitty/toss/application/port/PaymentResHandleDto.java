package io.nuabo.hikitty.toss.application.port;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentResHandleDto {
    private final String mId;
    private final String version;
    private final String paymentKey;
    private final String orderId;
    private final String orderName;
    private final String currency;
    private final String method;
    private final Long totalAmount;
    private final Long balanceAmount;
    private final Long suppliedAmount;
    private final Long vat;
    private final String status;
    private final String requestedAt;
    private final String approvedAt;
    private final String useEscrow;
    private final String cultureExpense;

    private final PaymentResHandleCardDto card;

    @Builder
    public PaymentResHandleDto(String mId, String version, String paymentKey, String orderId, String orderName, String currency, String method, Long totalAmount, Long balanceAmount, Long suppliedAmount, Long vat, String status, String requestedAt, String approvedAt, String useEscrow, String cultureExpense, PaymentResHandleCardDto card) {
        this.mId = mId;
        this.version = version;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.currency = currency;
        this.method = method;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.suppliedAmount = suppliedAmount;
        this.vat = vat;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.useEscrow = useEscrow;
        this.cultureExpense = cultureExpense;
        this.card = card;
    }
}
