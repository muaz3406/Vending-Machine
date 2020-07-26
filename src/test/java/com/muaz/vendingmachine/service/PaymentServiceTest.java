package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.enums.PaymentType;
import com.muaz.vendingmachine.exception.BadResourceRequestException;
import com.muaz.vendingmachine.repository.PaymentRequestRepository;
import org.junit.Before;
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

    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;

    @Before
    public void init() {
        paymentRequest = getMockCashPaymentRequest();
        paymentResponse = getMockPaymentResponse();
    }

    @Test(expected = BadResourceRequestException.class)
    public void shouldThrowExceptionWhenNodFoundOffer() {
        paymentRequest.setOffer(null);
        paymentService.doPay(paymentRequest);
    }

    @Test
    public void shouldPayWithCreditCardWhenRequestWithCreditCard() {
        paymentRequest.setPaymentType(PaymentType.CARD);
        String offerNumber = paymentRequest.getOffer().getOfferNumber();
        when(offerService.doOffer(paymentRequest, offerNumber)).thenReturn(paymentResponse);

        paymentService.doPay(paymentRequest);

        verify(creditCardPaymentService).doPay(paymentRequest, offerNumber);
    }

    @Test
    public void shouldPayWithCashWhenRequestedPaymentTypeWithCash() {
        String offerNumber = paymentRequest.getOffer().getOfferNumber();
        when(offerService.doOffer(paymentRequest, offerNumber)).thenReturn(paymentResponse);

        paymentService.doPay(paymentRequest);

        verify(cashPaymentService).doPay(paymentResponse, offerNumber);
    }

    @Test
    public void shouldReturnPaymentResponseWhenSuccessfulPayment() {
        when(offerService.doOffer(paymentRequest, paymentRequest.getOffer().getOfferNumber())).thenReturn(paymentResponse);

        PaymentResponse expectedPaymentResponse = paymentService.doPay(paymentRequest);

        verify(paymentRequestRepository).save(paymentRequest);
        assertEquals(expectedPaymentResponse, paymentResponse);
    }

}
