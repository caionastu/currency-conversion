package com.caionastu.currencyconversion.conversion.application.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ConversionResponse {

    private UUID id;
    private UUID userId;
    private String originCurrency;
    private double originValue;
    private String destinyCurrency;
    private double destinyValue;
    private double taxRate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date;
}
