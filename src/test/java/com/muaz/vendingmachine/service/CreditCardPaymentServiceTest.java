package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.exception.BadResourceRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.muaz.vendingmachine.provider.PaymentRequestProvider.getMockCardPaymentRequest;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardPaymentServiceTest {

    @InjectMocks
    private CreditCardPaymentService creditCardPaymentService;

    @Test(expected = BadResourceRequestException.class)
    public void shouldThrowExceptionWhenNotFoundCardInfo() {
        PaymentRequest paymentRequest = getMockCardPaymentRequest();
        paymentRequest.setCardInfo(null);

        creditCardPaymentService.doPay(paymentRequest, paymentRequest.getOffer().getOfferNumber());
    }
}