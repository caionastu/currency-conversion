package com.caionastu.currencyconversion.exchange.service;

import com.caionastu.currencyconversion.exchange.infrastructure.ExchangeService;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
class TaxRateCacheService {

    @Value("${exchange.api.cache.expire-time:12}")
    private int expireTime;

    private final ExchangeService exchangeService;

    private ExchangeResponse cache;
    private LocalDateTime lastUpdate;

    public ExchangeResponse get() {
        if (isCacheValid()) {
            return cache;
        }

        cache = exchangeService.getTaxRate();
        lastUpdate = LocalDateTime.now();

        return cache;
    }

    private boolean isCacheValid() {
        return !Objects.isNull(cache) && !isCacheExpired();
    }

    private boolean isCacheExpired() {
        return LocalDateTime.now().minusHours(expireTime).isAfter(lastUpdate);
    }
}
