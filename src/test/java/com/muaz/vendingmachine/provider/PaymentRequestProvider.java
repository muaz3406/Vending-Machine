package com.muaz.vendingmachine.provider;

import com.muaz.vendingmachine.entity.PaymentRequest;
import com.muaz.vendingmachine.enums.PaymentType;

import java.math.BigDecimal;

import static com.muaz.vendingmachine.provider.OfferProvider.getMockOffer;

public class PaymentRequestProvider {

    public static PaymentRequest getMockCashPaymentRequest() {
        return PaymentRequest.builder()
                .offer(getMockOffer())
                .money(BigDecimal.valueOf(20))
                .paymentType(PaymentType.BANKNOTE)
                .build();
    }
}
