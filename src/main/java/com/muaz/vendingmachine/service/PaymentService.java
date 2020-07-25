package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.enums.PaymentType;
import com.muaz.vendingmachine.repository.PaymentRequestRepository;
import com.muaz.vendingmachine.repository.PaymentResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CashPaymentService cashPaymentService;

    @Autowired
    private CreditCardPaymentService creditCardPaymentService;

    @Autowired
    private PaymentResponseRepository paymentResponseRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    public PaymentResponse doPay(PaymentRequest paymentRequest) {
        paymentRequestRepository.save(paymentRequest);

        String orderNumber = paymentRequest.getOffer().getOrderNumber();
        PaymentResponse paymentResponse = orderService.doOrder(paymentRequest, orderNumber);

        if (isCreditCard(paymentRequest)) {
            creditCardPaymentService.doPay(paymentRequest, orderNumber);
        } else {
            cashPaymentService.doPay(paymentResponse, orderNumber);
        }
        return paymentResponse;
    }

    private boolean isCreditCard(PaymentRequest paymentRequest) {
        PaymentType paymentType = paymentRequest.getPaymentType();
        return paymentType == PaymentType.REMOTE_CARD || paymentType == PaymentType.CARD;
    }
}
