package com.muaz.vendingmachine.service;

import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.entity.Product;
import com.muaz.vendingmachine.enums.PaymentType;
import com.muaz.vendingmachine.exception.NoSuchResourceFoundException;
import com.muaz.vendingmachine.repository.PaymentResponseRepository;
import com.muaz.vendingmachine.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.muaz.vendingmachine.provider.PaymentRequestProvider.getMockCashPaymentRequest;
import static com.muaz.vendingmachine.provider.PaymentResponseProvider.getMockPaymentResponse;
import static com.muaz.vendingmachine.provider.ProductProvider.getMockProduct;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

    private static final String OFFER_NUMBER = "testOffer9999";

    @InjectMocks
    private OfferService offerService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentResponseRepository paymentResponseRepository;

    private PaymentResponse paymentResponse;
    private Product product;

    @Before
    public void init() {
        paymentResponse = getMockPaymentResponse();
        product = getMockProduct();
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void shouldThrowExWhenNotEnoughProduct() {
        product.setCount(0);

        when(productRepository.findByProductNo(anyInt())).thenReturn(product);

        offerService.doOffer(getMockCashPaymentRequest(), OFFER_NUMBER);
    }

    @Test(expected = NoSuchResourceFoundException.class)
    public void shouldThrowExceptionWhenNotEnoughMoney() {
        paymentResponse.setPaymentType(PaymentType.BANKNOTE);
        paymentResponse.setRemainingPrice(BigDecimal.valueOf(-10));

        when(productRepository.findByProductNo(anyInt())).thenReturn(product);
        when(paymentResponseRepository.save(any(PaymentResponse.class))).thenReturn(paymentResponse);

        offerService.doOffer(getMockCashPaymentRequest(), OFFER_NUMBER);
    }

    @Test
    public void shouldReturnPaymentResponseWhenSuccessfulOffer() {
        when(productRepository.findByProductNo(anyInt())).thenReturn(product);
        when(paymentResponseRepository.save(any(PaymentResponse.class))).thenReturn(paymentResponse);

        PaymentResponse expectedPaymentResponse = offerService.doOffer(getMockCashPaymentRequest(), OFFER_NUMBER);

        assertEquals(expectedPaymentResponse.getPaymentType(), paymentResponse.getPaymentType());
        assertEquals(expectedPaymentResponse.getTotalPrice(), paymentResponse.getTotalPrice());
        assertEquals(expectedPaymentResponse.getCount(), paymentResponse.getCount());
    }

}
