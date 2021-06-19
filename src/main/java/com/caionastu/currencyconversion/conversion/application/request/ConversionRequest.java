package com.caionastu.currencyconversion.conversion.application.request;

import com.caionastu.currencyconversion.conversion.application.validator.ValidCurrency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ConversionRequest {

    @NotNull(message = "{conversion.userId.notNull}")
    private UUID userId;

    @ValidCurrency
    @NotBlank(message = "{conversion.origin.notBlank}")
    @Size(min = 3, max = 3, message = "{conversion.originCurrency.size}")
    private String originCurrency;

    @ValidCurrency
    @NotBlank(message = "{conversion.destiny.notBlank}")
    @Size(min = 3, max = 3, message = "{conversion.destinyCurrency.size}")
    private String destinyCurrency;

    @Positive(message = "{conversion.value.positive}")
    @NotNull(message = "{conversion.value.notNull}")
    private BigDecimal originValue;

    public boolean isSameCurrency() {
        return originCurrency.equals(destinyCurrency);
    }

    public String getOriginCurrency() {
        return originCurrency.toUpperCase();
    }

    public String getDestinyCurrency() {
        return destinyCurrency.toUpperCase();
    }
}
