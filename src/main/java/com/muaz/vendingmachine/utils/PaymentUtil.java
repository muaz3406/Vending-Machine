package com.muaz.vendingmachine.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muaz.vendingmachine.enums.PaymentType;

public class PaymentUtil {
    public static boolean isCash(PaymentType paymentType) {
        return paymentType == PaymentType.BANKNOTE || paymentType == PaymentType.COIN;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
