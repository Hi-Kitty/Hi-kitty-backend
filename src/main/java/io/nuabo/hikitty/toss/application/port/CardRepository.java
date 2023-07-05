package io.nuabo.hikitty.toss.application.port;

import io.nuabo.hikitty.toss.domain.Card;

public interface CardRepository {
    Card save(Card card);
}
