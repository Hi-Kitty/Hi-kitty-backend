package io.nuabo.hikitty.board.application.port;

import io.nuabo.hikitty.board.domain.Fundraiser;

public interface FundraiserRepository {
    Fundraiser save(Fundraiser fundraiser);

}
