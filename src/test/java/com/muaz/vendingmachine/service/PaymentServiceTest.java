package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.enums.PaymentType;
import com.muaz.vendingmachine.exception.BadResourceRequestException;
import com.muaz.vendingmachine.repository.PaymentRequestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.muaz.vendingmachine.provider.PaymentRequestProvider.getMockCashPaymentRequest;
import static com.muaz.vendingmachine.provider.PaymentResponseProvider.getMockPaymentResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private OfferService offerService;

    @Mock
    private CashPaymentService cashPaymentService;

    @Mock
    private CreditCardPaymentService creditCardPaymentService;

    @Mock
    private PaymentRequestRepository paymentRequestRepository;

    @Test(expected = BadResourceRequestException.class)
    public void shouldThrowExceptionWhenNodFoundOffer() {
        PaymentRequest paymentRequest = getMockCashPaymentRequest();
        paymentRequest.setOffer(null);

        paymentService.doPay(paymentRequest);
    }

    @Test
    public void shouldPayWithCreditCardWhenRequestWithCreditCard() {
        PaymentRequest paymentRequest = getMockCashPaymentRequest();
        paymentRequest.setPaymentType(PaymentType.CARD);
        String offerNumber = paymentRequest.getOffer().getOfferNumber();
        when(offerService.doOffer(paymentRequest, offerNumber)).thenReturn(getMockPaymentResponse());

        paymentService.doPay(paymentRequest);

        verify(creditCardPaymentService).doPay(paymentRequest, offerNumber);
    }

    @Test
    public void shouldPayWithCashWhenRequestedPaymentTypeWithCash() {
        PaymentRequest paymentRequest = getMockCashPaymentRequest();
        String offerNumber = paymentRequest.getOffer().getOfferNumber();
        PaymentResponse paymentResponse = getMockPaymentResponse();
        when(offerService.doOffer(paymentRequest, offerNumber)).thenReturn(paymentResponse);

        paymentService.doPay(paymentRequest);

        verify(cashPaymentService).doPay(paymentResponse, offerNumber);
    }

    @Test
    public void shouldReturnPaymentResponseWhenSuccessfulPayment() {
        PaymentRequest paymentRequest = getMockCashPaymentRequest();
        PaymentResponse paymentResponse = getMockPaymentResponse();
        when(offerService.doOffer(paymentRequest, paymentRequest.getOffer().getOfferNumber())).thenReturn(paymentResponse);

        PaymentResponse expectedPaymentResponse = paymentService.doPay(paymentRequest);

        verify(paymentRequestRepository).save(paymentRequest);
        assertEquals(paymentResponse, expectedPaymentResponse);
    }

}
