package io.nuabo.hikitty.toss.domain;

import io.nuabo.hikitty.toss.application.port.PaymentResHandleCardDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Card {

    private final Long id;
    private final String company; // "company", 카드사
    private final String number; // "number", 카드 번호
    private final String installmentPlanMonths; // "installmentPlanMonths", 할부 개월 수
    private final String isInterestFree; // "isInterestFree", 무이자 할부 여부
    private final String approveNo; // "approveNo", 승인 번호
    private final String useCardPoint; // "useCardPoint", 카드 포인트 사용 여부
    private final String cardType; // "cardType", 카드 타입
    private final String ownerType; // "ownerType", 개인
    private final String acquireStatus; // "acquireStatus", 매입 구분
    private final String receiptUrl; // "receiptUrl", 영수증 URL

    @Builder
    public Card(Long id, String company, String number, String installmentPlanMonths, String isInterestFree, String approveNo, String useCardPoint, String cardType, String ownerType, String acquireStatus, String receiptUrl) {
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

    public static Card from(PaymentResHandleCardDto card) {
        return Card.builder()
            .company(card.getCompany())
            .number(card.getNumber())
            .installmentPlanMonths(card.getInstallmentPlanMonths())
            .isInterestFree(card.getIsInterestFree())
            .approveNo(card.getApproveNo())
            .useCardPoint(card.getUseCardPoint())
            .cardType(card.getCardType())
            .ownerType(card.getOwnerType())
            .acquireStatus(card.getAcquireStatus())
            .receiptUrl(card.getReceiptUrl())
            .build();
    }
}
