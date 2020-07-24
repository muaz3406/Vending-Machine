package com.muaz.vendingmachine.controller;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    public PaymentResponse doPay(PaymentRequest paymentRequest) {
        log.info("PAYMENT START");
        return paymentService.doPay(paymentRequest);
    }
}
