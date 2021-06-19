package com.caionastu.currencyconversion.exchange.exception;

import com.caionastu.currencyconversion.common.exception.BusinessException;
import lombok.Getter;

public class SameCurrencyConversionException extends BusinessException {

    @Getter
    private final String currency;

    public SameCurrencyConversionException(String currency) {
        super("conversion.exception.sameCurrency", currency);
        this.currency = currency;
    }
}
