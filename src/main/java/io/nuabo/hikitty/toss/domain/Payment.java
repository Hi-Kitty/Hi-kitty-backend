package io.nuabo.hikitty.toss.domain;

import io.nuabo.hikitty.toss.application.port.PaymentResHandleDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Payment {

    private final Long id;
    private final String mId; // "tosspayments", 가맹점 Id
    private final String version; // "v1", API 버전
//    private final String paymentKey; // "paymentKey", 결제 고유 번호
//    private final String orderId; // "orderId", 우리가 만든 결제 고유 번호
    private final String orderName; // "orderName", 주문명
    private final String currency; // "currency", 통화
    private final String method; // "method", 결제 수단
    private final Long totalAmount; // "amount", 총 금액
    private final Long balanceAmount; // "amount",  결제 금액
    private final Long suppliedAmount; // "amount", 공급가액
    private final Long vat; // "amount", 부가세
    private final String status; // "status", 결제 상태
    private final String requestedAt; // "requestedAt", 결제 요청 시각
    private final String approvedAt; // "approvedAt", 결제 승인 시각
    private final String useEscrow; // "useEscrow", 에스크로 사용 여부
    private final String cultureExpense; // "cultureExpense", 문화비 지원 금액

    private final Order order;
    private final Card card;
    @Builder
    public Payment(Long id, String mId, String version, String orderName, String currency, String method, Long totalAmount, Long balanceAmount, Long suppliedAmount, Long vat, String status, String requestedAt, String approvedAt, String useEscrow, String cultureExpense, Order order, Card card) {
        this.id = id;
        this.mId = mId;
        this.version = version;
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
        this.order = order;
        this.card = card;
    }

    public static Payment from(PaymentResHandleDto handleDto, Order order, Card card) {
        return Payment.builder()
                .mId(handleDto.getMId())
                .version(handleDto.getVersion())
                .orderName(handleDto.getOrderName())
                .currency(handleDto.getCurrency())
                .method(handleDto.getMethod())
                .totalAmount(handleDto.getTotalAmount())
                .balanceAmount(handleDto.getBalanceAmount())
                .suppliedAmount(handleDto.getSuppliedAmount())
                .vat(handleDto.getVat())
                .status(handleDto.getStatus())
                .requestedAt(handleDto.getRequestedAt())
                .approvedAt(handleDto.getApprovedAt())
                .useEscrow(handleDto.getUseEscrow())
                .cultureExpense(handleDto.getCultureExpense())
                .order(order)
                .card(card)
                .build();
    }
}
