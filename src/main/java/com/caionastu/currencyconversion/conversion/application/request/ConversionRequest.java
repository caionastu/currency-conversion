package com.caionastu.currencyconversion.conversion.application.request;

import com.caionastu.currencyconversion.conversion.domain.Currencies;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConversionRequest {

    @NotBlank(message = "{conversion.user.notBlank}")
    private UUID userId;

    @NotNull(message = "{conversion.origin.notNull}")
    private Currencies originCurrency;

    @NotNull(message = "{conversion.destiny.notNull}")
    private Currencies destinyCurrency;

    @Positive(message = "{conversion.value.positive}")
    private double originValue;
}
