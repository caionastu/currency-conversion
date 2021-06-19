package com.caionastu.currencyconversion.exchange.service;

import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.exchange.exception.SameCurrencyConversionException;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxRateService {

    private static final int DEFAULT_SCALE = 6;

    private final TaxRateCacheService cacheService;

    public BigDecimal get(ConversionRequest request) {
        if (request.isSameCurrency()) {
            throw new SameCurrencyConversionException(request.getOriginCurrency());
        }

        ExchangeResponse response = cacheService.get();

        BigDecimal originRate = response.getTaxRate(request.getOriginCurrency());
        BigDecimal destinyRate = response.getTaxRate(request.getDestinyCurrency());

        return destinyRate.divide(originRate, DEFAULT_SCALE, RoundingMode.HALF_EVEN);
    }
}
