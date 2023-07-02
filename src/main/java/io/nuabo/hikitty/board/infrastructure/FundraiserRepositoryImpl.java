package io.nuabo.hikitty.board.infrastructure;

import io.nuabo.hikitty.board.application.port.FundraiserRepository;
import io.nuabo.hikitty.board.domain.Fundraiser;
import io.nuabo.hikitty.board.infrastructure.entity.FundraiserEntity;
import io.nuabo.hikitty.board.infrastructure.port.FundraiserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FundraiserRepositoryImpl implements FundraiserRepository {
    private final FundraiserJpaRepository fundraiserJpaRepository;

    @Override
    public Fundraiser save(Fundraiser fundraiser) {
        return fundraiserJpaRepository.save(FundraiserEntity.from(fundraiser)).toModel();
    }
}
