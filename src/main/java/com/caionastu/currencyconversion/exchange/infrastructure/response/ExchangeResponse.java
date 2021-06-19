package com.caionastu.currencyconversion.exchange.infrastructure.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ExchangeResponse {

    private Map<String, BigDecimal> rates = new HashMap<>();

    public BigDecimal getTaxRate(String currency) {
        return rates.get(currency);
    }

    public Map<String, BigDecimal> getRates() {
        return new HashMap<>(rates);
    }
}
