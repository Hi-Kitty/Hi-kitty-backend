package io.nuabo.hikitty.toss.infrastructure.port;

import io.nuabo.hikitty.toss.application.port.PaymentResHandleDto;
import io.nuabo.hikitty.toss.infrastructure.TossDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "toss", url = "https://api.tosspayments.com/v1/payments/")
public interface TossClient {

    @PostMapping("/{paymentKey}")
    ResponseEntity<PaymentResHandleDto> send(
            @RequestHeader HttpHeaders headers,
            @PathVariable("paymentKey") String paymentKey,
            @RequestBody TossDto tossDto
    );
}
