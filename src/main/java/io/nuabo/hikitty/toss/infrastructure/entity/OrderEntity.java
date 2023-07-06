package io.nuabo.hikitty.toss.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.toss.domain.PayType;
import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity(name = "orders")
@SQLDelete(sql = "update orders set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class OrderEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayType payType;


    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String orderNameType;
    private String customerEmail;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long boardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private String paymentKey;

    private String fundraiserName;

    public static OrderEntity from(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.id = order.getId();
        orderEntity.payType = order.getPayType();
        orderEntity.amount = order.getAmount();
        orderEntity.orderId = order.getOrderId();
        orderEntity.orderNameType = order.getOrderNameType();
        orderEntity.customerEmail = order.getCustomerEmail();
        orderEntity.customerName = order.getCustomerName();
        orderEntity.userId = order.getUserId();
        orderEntity.boardId = order.getBoardId();
        orderEntity.paymentStatus = order.getPaymentStatus();
        orderEntity.paymentKey = order.getPaymentKey();
        orderEntity.fundraiserName = order.getFundraiserName();
        return orderEntity;
    }

    public Order toModel() {
        return Order.builder()
                .id(id)
                .payType(payType)
                .amount(amount)
                .orderId(orderId)
                .orderNameType(orderNameType)
                .customerEmail(customerEmail)
                .customerName(customerName)
                .userId(userId)
                .boardId(boardId)
                .createAt(getCreateAt())
                .paymentStatus(paymentStatus)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .fundraiserName(fundraiserName)
                .build();
    }
}
