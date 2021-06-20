package com.caionastu.currencyconversion.exchange.exception;

import com.caionastu.currencyconversion.common.exception.BusinessException;

public class CommunicationFailedExchangeException extends BusinessException {

    public CommunicationFailedExchangeException() {
        super("exchange.exception.communicate");
    }
}
