package com.caionastu.currencyconversion.exchange.service;

import com.caionastu.currencyconversion.exchange.infrastructure.ExchangeService;
import com.caionastu.currencyconversion.exchange.infrastructure.response.ExchangeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
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
            log.info("Cached taxes rates is valid. Retrieving from cache.");
            return cache;
        }

        log.info("Cached taxes rates is not valid. Retrieving from Exchange Api.");
        cache = exchangeService.getTaxRate();
        lastUpdate = LocalDateTime.now();

        log.info("Cache updated.");

        return cache;
    }

    private boolean isCacheValid() {
        return !Objects.isNull(cache) && !isCacheExpired();
    }

    private boolean isCacheExpired() {
        return LocalDateTime.now().minusHours(expireTime).isAfter(lastUpdate);
    }
}
