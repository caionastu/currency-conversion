package com.caionastu.currencyconversion.exchange.infrastructure;

import com.caionastu.currencyconversion.exchange.exception.CommunicationFailedExchangeException;
import com.caionastu.currencyconversion.exchange.exception.FailToGetTaxRateException;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import feign.FeignException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExchangeServiceTest {

    private ExchangeClient client;
    private ExchangeService service;

    @BeforeAll
    void setup() {
        client = Mockito.mock(ExchangeClient.class);
        service = new ExchangeService(client);
        ReflectionTestUtils.setField(service, "apiKey", UUID.randomUUID().toString());
    }

    @Test
    @DisplayName("It should get tax rate.")
    void getTaxRate() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", BigDecimal.valueOf(1.23548));
        rates.put("USD", BigDecimal.valueOf(0.321548));
        when(client.getTaxRate(any())).thenReturn(ResponseEntity.ok(new ExchangeResponse(rates)));

        ExchangeResponse response = service.getTaxRate();

        assertThat(response.getTaxRate("BRL")).isEqualTo(rates.get("BRL"));
        assertThat(response.getTaxRate("USD")).isEqualTo(rates.get("USD"));
    }

    @Test
    @DisplayName("It should throw CommunicationFailedExchangeException if can't reach Exchange Api.")
    void failToCommunicateWithExchangeApi() {
        when(client.getTaxRate(any())).thenThrow(FeignException.class);

        assertThatThrownBy(() -> {
            service.getTaxRate();
        }).isInstanceOf(CommunicationFailedExchangeException.class);
    }

    @Test
    @DisplayName("It should throw FailToGetTaxRateException if response body from Exchange Api is empty.")
    void emptyResponseBody() {
        when(client.getTaxRate(any())).thenReturn(ResponseEntity.ok(null));

        assertThatThrownBy(() -> {
            service.getTaxRate();
        }).isInstanceOf(FailToGetTaxRateException.class);
    }
}
