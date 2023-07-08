package io.nuabo.hikitty.toss.presentation.response;

import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompleteResponse {
    private final String orderId;
    private final String orderName;
    private final String customerName;
    private final String customerEmail;
    private final String fundraiserName;
    private final Long amount;

    private final Long boardId;
    private final PaymentStatus paymentStatus;

    @Builder
    public CompleteResponse(String orderId, String orderName, String customerName, String customerEmail, String fundraiserName, Long amount, Long boardId, PaymentStatus paymentStatus) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.fundraiserName = fundraiserName;
        this.amount = amount;
        this.boardId = boardId;
        this.paymentStatus = paymentStatus;
    }

    public static CompleteResponse from(Order order) {
        return CompleteResponse.builder()
                .orderId(order.getOrderId())
                .orderName(order.getOrderNameType())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .fundraiserName(order.getFundraiserName())
                .amount(order.getAmount())
                .boardId(order.getBoardId())
                .paymentStatus(order.getPaymentStatus())
                .build();
    }
}
