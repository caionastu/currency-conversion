package com.caionastu.currencyconversion.exchange.infrastructure;

import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "exchange-client", url = "${exchange.api.url}")
interface ExchangeClient {

    @PostMapping
    ResponseEntity<ExchangeResponse> getTaxRate(@RequestParam(value = "access_key") String accessKey);
}
