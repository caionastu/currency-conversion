package com.caionastu.currencyconversion.exchange.infrastructure;

import com.caionastu.currencyconversion.exchange.exception.CommunicationFailedExchangeException;
import com.caionastu.currencyconversion.exchange.exception.FailToGetTaxRateException;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {

    @Value("${exchange.api.key}")
    private String apiKey;

    private final ExchangeClient client;

    public ExchangeResponse getTaxRate() {
        ResponseEntity<ExchangeResponse> responseEntity;

        try {
            log.info("Making a request to Exchange Api.");
            responseEntity = client.getTaxRate(apiKey);
        } catch (FeignException e) {
            log.error("Fail to communicate with Exchange Api.", e);
            throw new CommunicationFailedExchangeException();
        }

        ExchangeResponse response = responseEntity.getBody();
        if (response == null) {
            log.error("Fail to get tax rate from response body.");
            throw new FailToGetTaxRateException();
        }

        log.info("Successful response from Exchange Api.");
        return response;
    }
}
