package com.caionastu.currencyconversion.conversion.application.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@SpringBootTest
class CurrencyValidatorIT {

    @Autowired
    private ValidCurrencyValidator currencyValidator;

    @ParameterizedTest
    @ValueSource(strings = {"brl", "BrL", "USD", "JPY", "usd", "AED", "XAU", "BTC"})
    @DisplayName("It should validate the currency in database. All cases must return true.")
    void isValidTrue(String currency) {
        boolean isValid = currencyValidator.isValid(currency, null);
        assertThat(isValid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"bbb", "BBB", "ZZZ", "AAA", "Abc", "UII", "PPP"})
    @DisplayName("It should validate the currency in database. All cases must return false.")
    void isValidFalse(String currency) {
        boolean isValid = currencyValidator.isValid(currency, null);
        assertThat(isValid).isFalse();
    }

}
