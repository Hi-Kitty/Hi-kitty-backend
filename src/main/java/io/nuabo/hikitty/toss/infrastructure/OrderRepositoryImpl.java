package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.common.domain.exception.ResourceNotFoundException;
import io.nuabo.hikitty.board.presentation.request.PageNationRequest;
import io.nuabo.hikitty.toss.application.port.OrderRepository;
import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import io.nuabo.hikitty.toss.infrastructure.entity.OrderEntity;
import io.nuabo.hikitty.toss.infrastructure.port.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.from(order)).toModel();
    }

    @Override
    public Optional<Order> findByOrderId(String orderId) {
        return orderJpaRepository.findByOrderId(orderId).map(OrderEntity::toModel);
    }

    @Override
    public Order getByOrderId(String orderId) {
        return findByOrderId(orderId).orElseThrow(()-> new ResourceNotFoundException("Order", orderId));
    }

    @Override
    public List<Order> findAllByUserIdAndPaymentStatus(Long userId, PaymentStatus status) {
        return orderJpaRepository.findAllByUserIdAndPaymentStatus(userId, status).stream().map(OrderEntity::toModel).toList();
    }

    @Override
    public Page<Order> findPageAllByUserIdAndPaymentStatus(Long id, PaymentStatus paid, PageNationRequest pageNationRequest) {
        return orderJpaRepository.findAllByUserIdAndPaymentStatus(id, paid, getPageAndSize(pageNationRequest)).map(OrderEntity::toModel);
    }

    @Override
    public List<Order> findAllByBoardIdAndPaymentStatus(Long boardId, PaymentStatus paid) {
        return orderJpaRepository.findAllByBoardIdAndPaymentStatus(boardId, paid).stream().map(OrderEntity::toModel).toList();
    }

    private PageRequest getPageAndSize(PageNationRequest pageNationRequest) {
        return PageRequest.of(pageNationRequest.getPage(), pageNationRequest.getSize());
    }
}
