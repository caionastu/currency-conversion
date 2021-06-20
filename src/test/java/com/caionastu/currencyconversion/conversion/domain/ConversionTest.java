package com.caionastu.currencyconversion.conversion.domain;

import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
class ConversionTest {

    @Test
    @DisplayName("It should create a Conversion.")
    void createConversion() {
        UUID userId = UUID.randomUUID();
        ConversionRequest request = new ConversionRequest(
                userId,
                "BRL",
                "USD",
                BigDecimal.TEN);

        BigDecimal taxRate = BigDecimal.valueOf(1.89875);
        User user = new User(userId, "User Test Name");

        Conversion conversion = Conversion.from(request, taxRate, user);

        assertThat(conversion.getId()).isNull();
        assertThat(conversion.getUser().getId()).isEqualTo(user.getId());
        assertThat(conversion.getOriginCurrency().getInitials()).isEqualTo(request.getOriginCurrency());
        assertThat(conversion.getOriginCurrency().getInitials()).isEqualTo(request.getOriginCurrency());
        assertThat(conversion.getOriginValue()).isEqualTo(request.getOriginValue());
        assertThat(conversion.getDestinyCurrency().getInitials()).isEqualTo(request.getDestinyCurrency());
        assertThat(conversion.getTaxRate()).isEqualTo(taxRate);
    }

    @Test
    @DisplayName("It should throw NullPointException if creating Conversion with null user.")
    void nullUser() {
        UUID userId = UUID.randomUUID();
        ConversionRequest request = new ConversionRequest(
                userId,
                "BRL",
                "USD",
                BigDecimal.TEN);

        BigDecimal taxRate = BigDecimal.valueOf(1.89875);

        assertThatThrownBy(() -> Conversion.from(request, taxRate, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Thrown exception if creating Conversion with null tax rate.")
    void nullTaxRate() {
        UUID userId = UUID.randomUUID();
        ConversionRequest request = new ConversionRequest(
                userId,
                "BRL",
                "USD",
                BigDecimal.TEN);

        User user = new User(userId, "User Test Name");

        assertThatThrownBy(() -> Conversion.from(request, null, user))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Thrown exception if creating Conversion with null tax rate.")
    void nullRequest() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "User Test Name");
        BigDecimal taxRate = BigDecimal.valueOf(1.89875);

        assertThatThrownBy(() -> Conversion.from(null, taxRate, user))
                .isInstanceOf(NullPointerException.class);
    }
}
