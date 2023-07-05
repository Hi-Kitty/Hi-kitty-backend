package io.nuabo.hikitty.toss.infrastructure;

import io.nuabo.common.application.port.UuidHolder;
import io.nuabo.common.domain.exception.TossException;
import io.nuabo.hikitty.toss.application.port.*;
import io.nuabo.hikitty.toss.domain.Order;
import io.nuabo.hikitty.toss.domain.PaymentStatus;
import io.nuabo.hikitty.toss.application.port.TossServer;
import io.nuabo.hikitty.toss.infrastructure.port.TossClient;
import io.nuabo.hikitty.toss.infrastructure.port.TossConfig;
import io.nuabo.hikitty.toss.presentation.request.PaymentQueryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossServerImpl implements TossServer {

    private final OrderRepository orderRepository;
    private final TossConfig tossConfig;
    private final UuidHolder uuidHolder;
    private final TossClient tossClient;


    @Override
    public PaymentResHandleDto send(PaymentQueryRequest request, Order order) {
        String secretKey = tossConfig.getClientSecretKey() + ":";
        secretKey = uuidHolder.encode(secretKey);
        HttpHeaders httpHeaders = setHeader(secretKey);

        TossDto tossDto = TossDto.from(request);
        ResponseEntity<PaymentResHandleDto> paymentResponseEntity = tossClient.send(httpHeaders, request.getPaymentKey(), tossDto);

        if (paymentResponseEntity.getStatusCode().is4xxClientError() && paymentResponseEntity.getStatusCode().is5xxServerError()) {
            order = order.setPaymentStatus(PaymentStatus.FAILED);
            orderRepository.save(order);
            throw new TossException("TossException");
        }
        return paymentResponseEntity.getBody();
    }

    private HttpHeaders  setHeader(String secretKey) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(secretKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}
