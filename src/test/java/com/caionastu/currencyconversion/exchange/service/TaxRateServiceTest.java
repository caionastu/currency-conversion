package com.caionastu.currencyconversion.exchange.service;

import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.exchange.exception.SameCurrencyConversionException;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Tag("unit")
public class TaxRateServiceTest {

    private TaxRateService service;
    private TaxRateCacheService cacheService;

    @BeforeEach
    void setup() {
        cacheService = Mockito.mock(TaxRateCacheService.class);
        service = new TaxRateService(cacheService);
    }

    @ParameterizedTest
    @ValueSource(strings = {"brl", "BrL", "bRl", "BRL", "brL"})
    @DisplayName("It should throw SameCurrencyConversionException if two currencies are the same.")
    void sameCurrencyException(String currency) {
        ConversionRequest request = new ConversionRequest(
                UUID.randomUUID(),
                "BRL",
                currency,
                BigDecimal.TEN
        );

        Assertions.assertThatThrownBy(() -> {
            service.get(request);
        }).isInstanceOf(SameCurrencyConversionException.class);
    }

    @Test
    @DisplayName("It should get tax rate.")
    void getTaxRate() {
        ConversionRequest request = new ConversionRequest(
                UUID.randomUUID(),
                "BRL",
                "USD",
                BigDecimal.TEN
        );

        BigDecimal originTaxRate = BigDecimal.valueOf(3.21548);
        BigDecimal destinyTaxRate = BigDecimal.valueOf(1.23456);
        BigDecimal taxRateResult = destinyTaxRate.divide(originTaxRate, 6, RoundingMode.HALF_EVEN);

        Map<String, BigDecimal> rates = Maps.newHashMap("BRL", originTaxRate);
        rates.put("USD", destinyTaxRate);

        ExchangeResponse response = new ExchangeResponse(rates);
        when(cacheService.get()).thenReturn(response);

        BigDecimal taxRate = service.get(request);

        assertThat(taxRate).isEqualTo(taxRateResult);
    }
}
