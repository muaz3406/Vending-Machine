package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.Offer;
import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.enums.PaymentType;
import com.muaz.vendingmachine.exception.BadResourceRequestException;
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

        Offer offer = paymentRequest.getOffer();
        if (offer == null) {
            throw new BadResourceRequestException("NO FOUND OFFER");
        }
        String offerNumber = offer.getOfferNumber();
        PaymentResponse paymentResponse = orderService.doOrder(paymentRequest, offerNumber);

        if (isCreditCard(paymentRequest)) {
            creditCardPaymentService.doPay(paymentRequest, offerNumber);
        } else {
            cashPaymentService.doPay(paymentResponse, offerNumber);
        }
        return paymentResponse;
    }

    private boolean isCreditCard(PaymentRequest paymentRequest) {
        PaymentType paymentType = paymentRequest.getPaymentType();
        return paymentType == PaymentType.REMOTE_CARD || paymentType == PaymentType.CARD;
    }
}
