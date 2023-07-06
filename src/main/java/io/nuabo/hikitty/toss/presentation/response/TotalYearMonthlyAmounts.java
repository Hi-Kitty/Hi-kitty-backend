package io.nuabo.hikitty.toss.presentation.response;

import io.nuabo.hikitty.toss.domain.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class TotalYearMonthlyAmounts {
    private final Map<String, Integer> monthlyAmounts;

    private final Long totalAmount;

    @Builder
    public TotalYearMonthlyAmounts(Map<String, Integer> monthlyAmounts, Long totalAmount) {
        this.monthlyAmounts = monthlyAmounts;
        this.totalAmount = totalAmount;
    }

    public static TotalYearMonthlyAmounts from(List<Order> orders) {
        Map<String, Integer> monthlyAmounts = new LinkedHashMap<>();
        AtomicReference<Long> totalAmount = new AtomicReference<>(0L);
        orders.forEach(
                order -> {
                    monthlyAmounts.merge(order.getCreateAt().getYear() + "." + order.getCreateAt().getMonthValue(),
                            order.getAmount().intValue(), Integer::sum);
                    totalAmount.updateAndGet(v -> v + order.getAmount());
                }
        );
        return TotalYearMonthlyAmounts.builder()
                .monthlyAmounts(monthlyAmounts)
                .totalAmount(totalAmount.get())
                .build();
    }
}
