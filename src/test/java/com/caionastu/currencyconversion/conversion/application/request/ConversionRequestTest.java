package com.caionastu.currencyconversion.conversion.application.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class ConversionRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"usd", "EUR", "JPY", "XAU", "myr", "USD"})
    @DisplayName("It should return false for different currencies comparison, even if currency is in lowercase.")
    void differentCurrencies(String destinyCurrency) {
        ConversionRequest request = new ConversionRequest(
                UUID.randomUUID(),
                "BRL",
                destinyCurrency,
                BigDecimal.TEN
        );

        assertThat(request.isSameCurrency()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"BRL", "brl", "bRl", "BrL", "brL", "Brl"})
    @DisplayName("It should return true for same currencies comparison, even if currency is in lowercase.")
    void sameCurrencies(String destinyCurrency) {
        ConversionRequest request = new ConversionRequest(
                UUID.randomUUID(),
                "BRL",
                destinyCurrency,
                BigDecimal.TEN
        );

        assertThat(request.isSameCurrency()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"BRL", "brl", "bRl", "BrL", "brL", "Brl"})
    @DisplayName("It should return origin currency in uppercase.")
    void getOriginCurrencyWithUpperCase(String originCurrency) {
        ConversionRequest request = new ConversionRequest(
                UUID.randomUUID(),
                originCurrency,
                "USD",
                BigDecimal.TEN
        );

        assertThat(request.getOriginCurrency()).isEqualTo(originCurrency.toUpperCase());
    }

    @ParameterizedTest
    @ValueSource(strings = {"BRL", "brl", "bRl", "BrL", "brL", "Brl"})
    @DisplayName("It should return destiny currency in uppercase.")
    void getDestinyCurrencyWithUpperCase(String destinyCurrency) {
        ConversionRequest request = new ConversionRequest(
                UUID.randomUUID(),
                "USD",
                destinyCurrency,
                BigDecimal.TEN
        );

        assertThat(request.getDestinyCurrency()).isEqualTo(destinyCurrency.toUpperCase());
    }
}
