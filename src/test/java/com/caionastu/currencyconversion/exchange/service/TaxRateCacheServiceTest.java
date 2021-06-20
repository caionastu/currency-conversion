package com.caionastu.currencyconversion.exchange.service;

import com.caionastu.currencyconversion.exchange.infrastructure.ExchangeService;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
public class TaxRateCacheServiceTest {

    private ExchangeService exchangeService;
    private TaxRateCacheService cacheService;

    @BeforeEach
    void setup() {
        exchangeService = Mockito.mock(ExchangeService.class);
        cacheService = new TaxRateCacheService(exchangeService);
        ReflectionTestUtils.setField(cacheService, "expireTime", 12);
    }

    @Test
    @DisplayName("It should get tax rate from Exchange Api.")
    void getTaxRateFromExchangeApi() {
        ExchangeResponse response = new ExchangeResponse(Maps.newHashMap("BRL", BigDecimal.TEN));
        when(exchangeService.getTaxRate()).thenReturn(response);

        ExchangeResponse cacheResponse = cacheService.get();

        verify(exchangeService).getTaxRate();
        assertThat(cacheResponse.getTaxRate("BRL")).isEqualTo(response.getTaxRate("BRL"));
    }

    @Test
    @DisplayName("It should get tax rate from cache.")
    void getTaxRateFromCache() {
        ExchangeResponse response = new ExchangeResponse(Maps.newHashMap("BRL", BigDecimal.TEN));
        when(exchangeService.getTaxRate()).thenReturn(response);

        cacheService.get();
        ExchangeResponse cacheResponse = cacheService.get();

        verify(exchangeService).getTaxRate();
        assertThat(cacheResponse.getTaxRate("BRL")).isEqualTo(response.getTaxRate("BRL"));
    }

    @Test
    @DisplayName("It should update cache when is expired.")
    void updateExpiredCache() {
        ExchangeResponse response = new ExchangeResponse(Maps.newHashMap("BRL", BigDecimal.TEN));
        ReflectionTestUtils.setField(cacheService, "cache", response);
        ReflectionTestUtils.setField(cacheService, "lastUpdate", LocalDateTime.now().minusHours(15));

        when(exchangeService.getTaxRate()).thenReturn(response);

        ExchangeResponse cacheResponse = cacheService.get();

        verify(exchangeService).getTaxRate();
        assertThat(cacheResponse.getTaxRate("BRL")).isEqualTo(response.getTaxRate("BRL"));
    }

}
