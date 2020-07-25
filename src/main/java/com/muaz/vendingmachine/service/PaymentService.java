package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.Offer;
import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.exception.BadResourceRequestException;
import com.muaz.vendingmachine.repository.PaymentRequestRepository;
import com.muaz.vendingmachine.repository.PaymentResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.muaz.vendingmachine.utils.PaymentUtil.isCash;

@Service
public class PaymentService {

    @Autowired
    private OfferService offerService;

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
        PaymentResponse paymentResponse = offerService.doOffer(paymentRequest, offerNumber);

        if (isCash(paymentRequest.getPaymentType())) {
            creditCardPaymentService.doPay(paymentRequest, offerNumber);
        } else {
            cashPaymentService.doPay(paymentResponse, offerNumber);
        }
        return paymentResponse;
    }
}
