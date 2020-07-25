package com.muaz.vendingmachine.provider;

import com.muaz.vendingmachine.entity.Offer;

public class OfferProvider {

    public static Offer getMockOffer() {
        return Offer.builder()
                .count(2)
                .productNo(33)
                .offerNumber("aaa")
                .build();
    }
}
