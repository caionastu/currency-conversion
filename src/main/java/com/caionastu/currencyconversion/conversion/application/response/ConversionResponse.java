package com.caionastu.currencyconversion.conversion.application.response;

import com.caionastu.currencyconversion.conversion.domain.Conversion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ConversionResponse {

    private UUID id;
    private UUID userId;
    private String originCurrency;
    private BigDecimal originValue;
    private String destinyCurrency;
    private BigDecimal destinyValue;
    private BigDecimal taxRate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date;

    public static ConversionResponse from(Conversion conversion) {
        return new ConversionResponse(
                conversion.getId(),
                conversion.getUser().getId(),
                conversion.getOriginCurrency().getInitials(),
                conversion.getOriginValue(),
                conversion.getDestinyCurrency().getInitials(),
                conversion.getDestinyValue(),
                conversion.getTaxRate(),
                conversion.getDate());
    }
}
