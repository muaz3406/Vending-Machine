package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.exception.BadResourceRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.muaz.vendingmachine.enums.PaymentLogStatus.*;

@Service
@Slf4j
public class CreditCardPaymentService {

    public void doPay(PaymentRequest paymentRequest, String orderNumber) {
        log.info("status : {} orderNumber: {} ", CARD_PAY_START, orderNumber);

        if (StringUtils.isEmpty(paymentRequest.getCardInfo())) {
            log.info("status : {} orderNumber: {} ", CARD_PAY_FAIL, orderNumber);
            throw new BadResourceRequestException("INVALID CARD");
        }
        log.info("status : {} orderNumber: {} ", CARD_PAY_SUCCESS, orderNumber);
    }
}
