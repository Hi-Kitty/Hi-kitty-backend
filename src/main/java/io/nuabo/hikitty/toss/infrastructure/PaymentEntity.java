package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.toss.domain.PayType;
import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity(name = "payment")
@SQLDelete(sql = "update payment set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class PaymentEntity extends BaseTimeEntity {

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

    public static PaymentEntity from(Payment payment) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.id = payment.getId();
        paymentEntity.payType = payment.getPayType();
        paymentEntity.amount = payment.getAmount();
        paymentEntity.orderId = payment.getOrderId();
        paymentEntity.orderNameType = payment.getOrderNameType();
        paymentEntity.customerEmail = payment.getCustomerEmail();
        paymentEntity.customerName = payment.getCustomerName();
        paymentEntity.userId = payment.getUserId();
        paymentEntity.boardId = payment.getBoardId();
        paymentEntity.paymentStatus = payment.getPaymentStatus();
        paymentEntity.paymentKey = payment.getPaymentKey();
        return paymentEntity;
    }

    public Payment toModel() {
        return Payment.builder()
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
                .build();
    }
}
