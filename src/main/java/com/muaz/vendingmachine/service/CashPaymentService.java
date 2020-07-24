package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.muaz.vendingmachine.enums.PaymentLogStatus.CASH_PAY_SUCCESS;

@Service
@Slf4j
public class CashPaymentService {

    public void doPay(PaymentResponse paymentResponse, String orderNumber) {
        log.info("status : {} orderNumber: {} ", CASH_PAY_SUCCESS, orderNumber);
    }
}
