package com.muaz.vendingmachine.enums;

public enum PaymentLogStatus {
    ORDER_START,
    ORDER_FAIL,

    CASH_PAY_START,
    CASH_PAY_FAIL,
    CASH_PAY_SUCCESS,

    CARD_PAY_START,
    CARD_PAY_FAIL,
    CARD_PAY_REQUEST,
    CARD_PAY_RESPONSE,
    CARD_PAY_SUCCESS
}
