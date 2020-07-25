package com.muaz.vendingmachine.utils;

import com.muaz.vendingmachine.enums.PaymentType;

public class PaymentUtil {
    public static boolean isCash(PaymentType paymentType) {
        return paymentType == PaymentType.BANKNOTE || paymentType == PaymentType.COIN;
    }
}
