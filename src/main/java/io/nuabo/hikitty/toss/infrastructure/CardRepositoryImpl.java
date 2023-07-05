package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.hikitty.toss.application.port.CardRepository;
import io.nuabo.hikitty.toss.domain.Card;
import io.nuabo.hikitty.toss.infrastructure.entity.CardEntity;
import io.nuabo.hikitty.toss.infrastructure.port.CardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepository {

    private final CardJpaRepository cardJpaRepository;

    @Override
    public Card save(Card card) {
        return cardJpaRepository.save(CardEntity.from(card)).toModel();
    }
}
