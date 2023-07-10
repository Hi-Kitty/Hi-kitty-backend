package io.nuabo.hikitty.toss.domain;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.toss.presentation.request.OrderRequest;
import io.nuabo.hikitty.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Order {

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

    private final String fundraiserName;
    @Builder
    public Order(Long id, PayType payType, Long amount, String orderId, String orderNameType, String customerEmail, String customerName, Long userId, Long boardId, LocalDateTime createAt, PaymentStatus paymentStatus, String paymentKey, String fundraiserName) {
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
        this.fundraiserName = fundraiserName;
    }

    public static Order from(OrderRequest request, ClockHolder clockHolder, User user, Board board) {
        return Order.builder()
                .payType(request.getPayType())
                .amount(request.getAmount())
                .orderId(user.getId() + "_" + clockHolder.hashedOrderId(user.getId(), user.getName(), request.getAmount()))
                .orderNameType(board.getTitle())
                .customerEmail(user.getEmail())
                .customerName(user.getName())
                .userId(user.getId())
                .boardId(board.getId())
                .paymentStatus(PaymentStatus.READY)
                .fundraiserName(board.getFundraiserName())
                .build();
    }

    public Order setPaymentKey(String paymentKey) {
        return Order.builder()
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
                .fundraiserName(this.fundraiserName)
                .build();
    }

    public Order setPaymentStatus(PaymentStatus status) {
        return Order.builder()
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
                .paymentStatus(status)
                .paymentKey(this.paymentKey)
                .fundraiserName(this.fundraiserName)
                .build();
    }
}
