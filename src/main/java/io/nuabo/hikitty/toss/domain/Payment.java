package io.nuabo.hikitty.toss.domain;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.hikitty.toss.presentation.request.PaymentRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Payment {

    private final Long id;

    private final PayType payType;

    private final Long amount;

    private final String orderId;

    private final String orderNameType;

    private final String customerEmail;

    private final String customerName;

    private final Long userId;

    private final Long boardId;

    private final LocalDateTime createAt;

    private final PaymentStatus paymentStatus;

    private final String paymentKey;

    @Builder
    public Payment(Long id, PayType payType, Long amount, String orderId, String orderNameType, String customerEmail, String customerName, Long userId, Long boardId, LocalDateTime createAt, PaymentStatus paymentStatus, String paymentKey) {
        this.id = id;
        this.payType = payType;
        this.amount = amount;
        this.orderId = orderId;
        this.orderNameType = orderNameType;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.userId = userId;
        this.boardId = boardId;
        this.createAt = createAt;
        this.paymentStatus = paymentStatus;
        this.paymentKey = paymentKey;
    }

    public static Payment from(PaymentRequest request, ClockHolder clockHolder, Long userId) {
        return Payment.builder()
                .payType(request.getPayType())
                .amount(request.getAmount())
                .orderId(userId + "_" + clockHolder.hashedOrderId(userId, request.getCustomerName(), request.getAmount()))
                .orderNameType(request.getOrderName())
                .customerEmail(request.getCustomerEmail())
                .customerName(request.getCustomerName())
                .userId(userId)
                .boardId(request.getBoardId())
                .paymentStatus(PaymentStatus.READY)
                .build();
    }

    public Payment setPaymentKey(String paymentKey) {
        return Payment.builder()
                .id(this.id)
                .payType(this.payType)
                .amount(this.amount)
                .orderId(this.orderId)
                .orderNameType(this.orderNameType)
                .customerEmail(this.customerEmail)
                .customerName(this.customerName)
                .userId(this.userId)
                .boardId(this.boardId)
                .createAt(this.createAt)
                .paymentStatus(this.paymentStatus)
                .paymentKey(paymentKey)
                .build();
    }
}
