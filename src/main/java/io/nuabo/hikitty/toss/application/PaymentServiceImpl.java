package io.nuabo.hikitty.toss.application;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.domain.exception.PaymentException;
import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.board.domain.Board;
import io.nuabo.hikitty.board.presentation.request.PageNationRequest;
import io.nuabo.hikitty.toss.application.port.*;
import io.nuabo.hikitty.toss.domain.Card;
import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import io.nuabo.hikitty.toss.infrastructure.port.TossConfig;
import io.nuabo.hikitty.toss.presentation.BoardYearMonthlyAmounts;
import io.nuabo.hikitty.toss.presentation.port.PaymentService;
import io.nuabo.hikitty.toss.presentation.request.PaymentFailRequest;
import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;
import io.nuabo.hikitty.toss.presentation.request.OrderRequest;
import io.nuabo.hikitty.toss.presentation.response.*;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final TossConfig tossConfig;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;

    private final TossServer tossServer;

    @Transactional
    @Override
    public OrderResponse request(OrderRequest request, String email) {
        User user = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);
        Board board = boardRepository.getById(request.getBoardId());

        // TODO: 원래는 검증 로직이 필요
        Order order = Order.from(request, clockHolder, user.getId(), board.getFundraiserName());
        order = orderRepository.save(order);
        return OrderResponse.from(order, tossConfig);
    }

    @Transactional
    @Override
    public Payment process(PaymentQueryRequest request) {
        Order order = verify(request);
        PaymentResHandleDto handleDto = tossServer.send(request, order);
        return create(handleDto);
    }
    @Transactional
    @Override
    public PaymentResponse increaseBoard(Payment payment) {
        Board board = increaseAmountFromBoard(payment);
        return PaymentResponse.from(payment, board);
    }

    @Transactional
    @Override
    public void fail(PaymentFailRequest request) {
        Order order = orderRepository.getByOrderId(request.getOrderId());
        order.setPaymentStatus(PaymentStatus.FAILED);
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void increaseBoardNotResponse(Payment payment) {
        increaseAmountFromBoard(payment);
    }

    @Transactional(readOnly = true)
    @Override
    public CompleteResponse getByOrderId(String orderId) {
        Order order = orderRepository.getByOrderId(orderId);
        return CompleteResponse.from(order);
    }

    @Override
    public TotalAmountResponse getByEmail(String email) {
        User user = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);
        List<Order> orders = orderRepository.findAllByUserIdAndPaymentStatus(user.getId(), PaymentStatus.PAID);

        int sum = orders.stream().mapToInt(order -> order.getAmount().intValue()).sum();
        return TotalAmountResponse.from(orders.size(), sum);
    }

    @Override
    public Page<CompleteResponse> getOrderPageByEmail(String email, PageNationRequest pageNationRequest) {
        User user = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);
        Page<Order> orders = orderRepository.findPageAllByUserIdAndPaymentStatus(user.getId(), PaymentStatus.PAID, pageNationRequest);

        return orders.map(CompleteResponse::from);
    }
    @Override
    public BoardYearMonthlyAmounts checkByMonth(String email, Long boardId) {
        Board board = boardRepository.getById(boardId);
        List<Order> orders = orderRepository.findAllByBoardIdAndPaymentStatus(boardId, PaymentStatus.PAID);
        TotalYearMonthlyAmounts totalYearMonthlyAmounts = TotalYearMonthlyAmounts.from(orders);

        return BoardYearMonthlyAmounts.from(board, totalYearMonthlyAmounts);

    }


    private Order verify(PaymentQueryRequest request) {
        Order order = orderRepository.getByOrderId(request.getOrderId());

        if (order.getAmount().equals(request.getAmount())) {
            order = order.setPaymentKey(request.getPaymentKey());
            order = orderRepository.save(order);
        } else {
            order = order.setPaymentStatus(PaymentStatus.FAILED);
            orderRepository.save(order);
            throw new PaymentException("결제 금액이 일치하지 않습니다.");
        }
        return order;
    }

    private Payment create(PaymentResHandleDto handleDto) {

        Order order = orderRepository.getByOrderId(handleDto.getOrderId());
        order = order.setPaymentStatus(PaymentStatus.PAID);
        order = orderRepository.save(order);
        Card card = Card.from(handleDto.getCard());
        card = cardRepository.save(card);
        Payment payment = Payment.from(handleDto, order, card);
        return paymentRepository.save(payment);
    }


    private Board increaseAmountFromBoard(Payment payment) {
        Board board = boardRepository.getById(payment.getOrder().getBoardId());
        board = board.increasePaidAmount(payment.getBalanceAmount());
        return boardRepository.save(board);
    }

}
