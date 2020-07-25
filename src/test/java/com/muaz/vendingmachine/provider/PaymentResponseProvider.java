package com.muaz.vendingmachine.provider;

import com.muaz.vendingmachine.entity.PaymentResponse;
import com.muaz.vendingmachine.enums.PaymentType;

import java.math.BigDecimal;

public class PaymentResponseProvider {

    public static PaymentResponse getMockPaymentResponse() {
        return PaymentResponse.builder()
                .paymentType(PaymentType.CARD)
                .count(5)
                .totalPrice(BigDecimal.TEN)
                .build();
    }
}
