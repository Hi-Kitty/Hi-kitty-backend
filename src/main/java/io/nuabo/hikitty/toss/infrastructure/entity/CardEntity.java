package io.nuabo.hikitty.toss.infrastructure.entity;

import io.nuabo.common.infrastructure.BaseTimeEntity;
import io.nuabo.hikitty.toss.domain.Card;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity(name = "card")
@SQLDelete(sql = "update card set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class CardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company; // "company", 카드사
    private String number; // "number", 카드 번호
    private String installmentPlanMonths; // "installmentPlanMonths", 할부 개월 수
    private String isInterestFree; // "isInterestFree", 무이자 할부 여부
    private String approveNo; // "approveNo", 승인 번호
    private String useCardPoint; // "useCardPoint", 카드 포인트 사용 여부
    private String cardType; // "cardType", 카드 타입
    private String ownerType; // "ownerType", 개인
    private String acquireStatus; // "acquireStatus", 매입 구분
    private String receiptUrl; // "receiptUrl", 영수증 URL

    public static CardEntity from(Card card) {
        CardEntity cardEntity = new CardEntity();
        cardEntity.id = card.getId();
        cardEntity.company = card.getCompany();
        cardEntity.number = card.getNumber();
        cardEntity.installmentPlanMonths = card.getInstallmentPlanMonths();
        cardEntity.isInterestFree = card.getIsInterestFree();
        cardEntity.approveNo = card.getApproveNo();
        cardEntity.useCardPoint = card.getUseCardPoint();
        cardEntity.cardType = card.getCardType();
        cardEntity.ownerType = card.getOwnerType();
        cardEntity.acquireStatus = card.getAcquireStatus();
        cardEntity.receiptUrl = card.getReceiptUrl();
        return cardEntity;
    }

    public Card toModel() {
        return Card.builder()
            .id(id)
            .company(company)
            .number(number)
            .installmentPlanMonths(installmentPlanMonths)
            .isInterestFree(isInterestFree)
            .approveNo(approveNo)
            .useCardPoint(useCardPoint)
            .cardType(cardType)
            .ownerType(ownerType)
            .acquireStatus(acquireStatus)
            .receiptUrl(receiptUrl)
            .build();
    }
}
