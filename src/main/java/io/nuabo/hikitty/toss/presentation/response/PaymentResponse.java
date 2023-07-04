package io.nuabo.hikitty.toss.presentation.response;

import io.nuabo.hikitty.toss.application.port.TossConfig;
import io.nuabo.hikitty.toss.domain.PayType;
import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentResponse {

    private final PayType payType;

    private final Long amount;

    private final String orderName;

    private final String customerName;

    private final String customerEmail;

    private final String orderId;

    private final String successUrl;

    private final String failUrl;

    private final LocalDateTime createAt;

    private final PaymentStatus paymentStatus;

    @Builder
    public PaymentResponse(PayType payType, Long amount, String orderName, String customerName, String customerEmail, String orderId, String successUrl, String failUrl, LocalDateTime createAt, PaymentStatus paymentStatus) {
        this.payType = payType;
        this.amount = amount;
        this.orderName = orderName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderId = orderId;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
        this.createAt = createAt;
        this.paymentStatus = paymentStatus;
    }

    public static PaymentResponse from(Payment payment, TossConfig tossConfig) {
        return PaymentResponse.builder()
                .payType(payment.getPayType())
                .amount(payment.getAmount())
                .orderName(payment.getOrderNameType())
                .customerName(payment.getCustomerName())
                .customerEmail(payment.getCustomerEmail())
                .orderId(payment.getOrderId())
                .successUrl(tossConfig.getSuccessUrl())
                .failUrl(tossConfig.getFailUrl())
                .createAt(payment.getCreateAt())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }
}
