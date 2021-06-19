package com.caionastu.currencyconversion.exchange.exception;

import com.caionastu.currencyconversion.common.exception.BusinessException;

public class FailToGetTaxRateException extends BusinessException {

    public FailToGetTaxRateException() {
        super("exchange.exception.getRates");
    }
}
