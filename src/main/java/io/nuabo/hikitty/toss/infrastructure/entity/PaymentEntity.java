package io.nuabo.hikitty.toss.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.toss.domain.Payment;
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

    private String mId; // "tosspayments", 가맹점 Id
    private String version; // "v1", API 버전
//    private String paymentKey; // "paymentKey", 결제 고유 번호
//    private String orderId; // "orderId", 우리가 만든 결제 고유 번호
    private String orderName; // "orderName", 주문명
    private String currency; // "currency", 통화
    private String method; // "method", 결제 수단
    private Long totalAmount; // "amount", 총 금액
    private Long balanceAmount; // "amount",  결제 금액
    private Long suppliedAmount; // "amount", 공급가액
    private Long vat; // "amount", 부가세
    private String status; // "status", 결제 상태
    private String requestedAt; // "requestedAt", 결제 요청 시각
    private String approvedAt; // "approvedAt", 결제 승인 시각
    private String useEscrow; // "useEscrow", 에스크로 사용 여부
    private String cultureExpense; // "cultureExpense", 문화비 지원 금액

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private OrderEntity order;

    public static PaymentEntity from(Payment payment) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.mId = payment.getMId();
        paymentEntity.version = payment.getVersion();
        paymentEntity.orderName = payment.getOrderName();
        paymentEntity.currency = payment.getCurrency();
        paymentEntity.method = payment.getMethod();
        paymentEntity.totalAmount = payment.getTotalAmount();
        paymentEntity.balanceAmount = payment.getBalanceAmount();
        paymentEntity.suppliedAmount = payment.getSuppliedAmount();
        paymentEntity.vat = payment.getVat();
        paymentEntity.status = payment.getStatus();
        paymentEntity.requestedAt = payment.getRequestedAt();
        paymentEntity.approvedAt = payment.getApprovedAt();
        paymentEntity.useEscrow = payment.getUseEscrow();
        paymentEntity.cultureExpense = payment.getCultureExpense();
        paymentEntity.order = OrderEntity.from(payment.getOrder());
        return paymentEntity;
    }

    public Payment toModel() {
        return Payment.builder()
                .id(id)
                .mId(mId)
                .version(version)
                .orderName(orderName)
                .currency(currency)
                .method(method)
                .totalAmount(totalAmount)
                .balanceAmount(balanceAmount)
                .suppliedAmount(suppliedAmount)
                .vat(vat)
                .status(status)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .useEscrow(useEscrow)
                .cultureExpense(cultureExpense)
                .order(order.toModel())
                .build();
    }
}
