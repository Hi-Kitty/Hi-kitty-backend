package io.nuabo.hikitty.toss.application;

import io.nuabo.common.application.port.ClockHolder;
import io.nuabo.common.domain.exception.PaymentException;
import io.nuabo.hikitty.board.application.port.BoardRepository;
import io.nuabo.hikitty.toss.application.port.PaymentRepository;
import io.nuabo.hikitty.toss.application.port.TossConfig;
import io.nuabo.hikitty.toss.presentation.port.PaymentService;
import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;
import io.nuabo.hikitty.toss.presentation.request.PaymentRequest;
import io.nuabo.hikitty.toss.domain.Payment;
import io.nuabo.hikitty.toss.presentation.response.PaymentResponse;
import io.nuabo.hikitty.user.application.port.UserRepository;
import io.nuabo.hikitty.user.domain.User;
import io.nuabo.hikitty.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final TossConfig tossConfig;

    private final UserRepository userRepository;

    private final ClockHolder clockHolder;

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public PaymentResponse request(PaymentRequest request, String email) {
        User user = userRepository.getByEmailAndStatus(email, UserStatus.ACTIVE);
        boardRepository.getById(request.getBoardId());
        // TODO: 원래는 검증 로직이 필요
        Payment payment = Payment.from(request, clockHolder, user.getId());
        payment = paymentRepository.save(payment);
        return PaymentResponse.from(payment, tossConfig);
    }

    @Transactional
    @Override
    public void verify(PaymentQueryRequest request) {
        Payment payment = paymentRepository.getByOrderId(request.getOrderId());

        if (payment.getAmount().equals(request.getAmount())) {
            payment = payment.setPaymentKey(request.getPaymentKey());
            paymentRepository.save(payment);
        } else {
            throw new PaymentException("결제 금액이 일치하지 않습니다.");
        }
    }

//    private String requestFinal(PaymentQueryRequest request) {
//
//    }
}
