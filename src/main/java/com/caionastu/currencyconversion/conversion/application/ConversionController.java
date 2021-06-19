package com.caionastu.currencyconversion.conversion.application;

import com.caionastu.currencyconversion.common.application.annotation.ApiPageable;
import com.caionastu.currencyconversion.common.application.response.ApiCollectionResponse;
import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.conversion.application.response.ConversionResponse;
import com.caionastu.currencyconversion.conversion.domain.Conversion;
import com.caionastu.currencyconversion.conversion.repository.ConversionRepository;
import com.caionastu.currencyconversion.exchange.service.TaxRateService;
import com.caionastu.currencyconversion.user.domain.User;
import com.caionastu.currencyconversion.user.exception.UserNotFoundException;
import com.caionastu.currencyconversion.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/conversions")
@AllArgsConstructor
@Slf4j
public class ConversionController {

    private final UserRepository userRepository;
    private final ConversionRepository repository;
    private final TaxRateService taxRateService;

    @ApiPageable
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<ApiCollectionResponse<ConversionResponse>> findByUser(@PathVariable UUID userId, @ApiIgnore Pageable pageable) {
        Page<ConversionResponse> conversions = repository.findByUserId(userId, pageable)
                .map(ConversionResponse::from);

        ApiCollectionResponse<ConversionResponse> response = ApiCollectionResponse.from(conversions);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ConversionResponse> convert(@RequestBody @Valid ConversionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        BigDecimal taxRate = taxRateService.get(request);
        Conversion conversion = Conversion.from(request, taxRate, user);

        repository.save(conversion);

        ConversionResponse response = ConversionResponse.from(conversion);
        return ResponseEntity.ok(response);
    }

}
