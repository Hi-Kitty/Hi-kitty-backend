package io.nuabo.hikitty.toss.presentation.response;

import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.toss.domain.Payment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentResponse {
    private final Long boardId;
    private final String customerName;
    private final String customerEmail;
    private final String boardName;
    private final String fundraiserName;
    private final Long balanceAmount;

    private final Long paymentId;
    private final Long orderId;

    @Builder
    public PaymentResponse(Long boardId, String customerName, String customerEmail, String boardName, String fundraiserName, Long balanceAmount, Long paymentId, Long orderId) {
        this.boardId = boardId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.boardName = boardName;
        this.fundraiserName = fundraiserName;
        this.balanceAmount = balanceAmount;
        this.paymentId = paymentId;
        this.orderId = orderId;
    }

    public static PaymentResponse from(Payment payment, Board board) {
        return PaymentResponse.builder()
                .boardId(board.getId())
                .customerName(payment.getOrder().getCustomerName())
                .customerEmail(payment.getOrder().getCustomerEmail())
                .boardName(payment.getOrderName())
                .fundraiserName(board.getFundraiserName())
                .balanceAmount(payment.getBalanceAmount())
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .build();

    }
}
