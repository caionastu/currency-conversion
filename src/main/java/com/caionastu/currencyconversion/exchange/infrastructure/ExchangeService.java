package com.caionastu.currencyconversion.exchange.infrastructure;

import com.caionastu.currencyconversion.exchange.exception.FailToGetTaxRateException;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    @Value("${exchange.api.key}")
    private String apiKey;

    private final ExchangeClient client;

    public ExchangeResponse getTaxRate() {
        // TODO: 19-Jun-21 Handle timeout and errors
        // TODO: 19-Jun-21 Do try catch, log FeingClientError, throw FeignClientError
        ResponseEntity<ExchangeResponse> responseEntity = client.getTaxRate(apiKey);

        if (responseEntity.getBody() == null) {
            throw new FailToGetTaxRateException();
        }

        return responseEntity.getBody();
    }
}
