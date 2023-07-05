package io.nuabo.hikitty.toss.application.port;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentResHandleCardDto {
    private final Long id;
    private final String company;
    private final String number;
    private final String installmentPlanMonths;
    private final String isInterestFree;
    private final String approveNo;
    private final String useCardPoint;
    private final String cardType;
    private final String ownerType;
    private final String acquireStatus;
    private final String receiptUrl;

    @Builder
    public PaymentResHandleCardDto(Long id, String company, String number, String installmentPlanMonths, String isInterestFree, String approveNo, String useCardPoint, String cardType, String ownerType, String acquireStatus, String receiptUrl) {
        this.id = id;
        this.company = company;
        this.number = number;
        this.installmentPlanMonths = installmentPlanMonths;
        this.isInterestFree = isInterestFree;
        this.approveNo = approveNo;
        this.useCardPoint = useCardPoint;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.acquireStatus = acquireStatus;
        this.receiptUrl = receiptUrl;
    }
}
